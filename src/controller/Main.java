package controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import config.Config;
import config.Const;
import config.Options;
import scenariorunner.ScenarioRunner;
import util.RuberDriverLoggerFormatter;

public class Main {

    static public Options option = null;
    static public Config cfg = null;
    static public WebDriverManager driverManager = new WebDriverManager();
    static public HashMap<String, ScenarioRunner> runnerManager = new HashMap<>();
    static public Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public static void main(String[] args) {

        Main main = new Main();
        option = new Options().build(args);
        cfg = new Config();
        main.setLogger();
        logger.log(Level.INFO, "INITIATED RUBERDRIVER");

        if (option.isShowVersion()) {
            System.out.println(Const.VERSION);
            System.exit(0);
        }

        boolean isAll = option.isAllScenario();
        Collection<String> scenarios = isAll ? cfg.getScenarios().keySet() : option.getScenarioList();

        if (option.isAsync()) {
            main.runAsync(scenarios);
        } else {
            main.runSync(scenarios);
        }

        main = null;
        option = null;
        cfg = null;

        System.out.println("========================");
        Collection<String> keys = driverManager.getDriverList();
        for (String key : keys) {
            WebDriver driver = driverManager.getDriver(key);
            if (driver != null) {
                logger.log(Level.WARNING, "NOT CLOSED : " + key);
            }
        }
        System.out.println("========================");
        for (ScenarioRunner runner : runnerManager.values()) {
            String msg = runner.getFinishStatusString() + " " + runner.getDriverKey();
            runner.getLogger().log(Level.INFO, msg);
            logger.log(Level.INFO, msg);
        }
        logger.log(Level.INFO, "QUIT RUBERDRIVER");
    }

    public void setLogger() {
        logger.setLevel(Level.ALL);
        try {
            String date = LocalDate.now().toString();
            String time = LocalTime.now().toString();
            FileHandler fileTxt = new FileHandler(Main.cfg.getLogPath() + "/ruberdriver--" + date + "--" + time + ".log");
            fileTxt.setFormatter(new RuberDriverLoggerFormatter());
            logger.addHandler(fileTxt);
        } catch (SecurityException | IOException e) {
            e.printStackTrace();
        }
    }

    public void runSync(Collection<String> scenarios) {

        for (String s : scenarios) {
            String s_name = get_scenario_name(s);
            int s_count = get_loop_count(s);
            for (int i = 0; i < s_count; i++) {
                new Ruberdriver(s_name).run();
            }
        }
        scenarios = null;
    }

    public void runAsync(Collection<String> scenarios) {

        LinkedList<Thread> threads = new LinkedList<>();

        for (String s : scenarios) {

            String s_name = get_scenario_name(s);
            int s_count = get_loop_count(s);

            for (int i = 0; i < s_count; i++) {
                Thread rd = new Ruberdriver(s_name);
                threads.add(rd);
                rd.start();
            }
        }

        try {
            for (Thread thread : threads) {
                thread.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return;
    }

    private int get_loop_count(String scenario) {

        String str = scenario.trim();
        if (str.matches("^.+\\:\\s*\\d+\\s*$")) {
            int colon_loc = 1 + str.lastIndexOf(":");
            return Integer.parseInt(str.substring(colon_loc).trim());
        }
        return 1;
    }

    private String get_scenario_name(String scenario) {

        String str = scenario.trim();
        if (str.matches("^.+\\:\\s*(\\d+\\s*)?$")) {
            int colon_loc = str.lastIndexOf(":");
            return str.substring(0, colon_loc).trim();
        }
        return str;
    }

    @Test
    public void test_get_loop_count() {
        Assert.assertEquals(1, get_loop_count("test"));
        Assert.assertEquals(2, get_loop_count("test:2"));
        Assert.assertEquals(1, get_loop_count("test:"));
        Assert.assertEquals(1, get_loop_count("t:est:"));
        Assert.assertEquals(23, get_loop_count(" test: 23 "));
    }

    @Test
    public void test_get_scenario_name() {
        Assert.assertTrue("test".equals(get_scenario_name("test")));
        Assert.assertTrue("test".equals(get_scenario_name("test:2")));
        Assert.assertTrue("test".equals(get_scenario_name("test:")));
        Assert.assertTrue("t:est".equals(get_scenario_name("t:est:")));
        Assert.assertTrue("test".equals(get_scenario_name(" test: 23 ")));
    }

}
