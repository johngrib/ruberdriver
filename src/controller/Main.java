package controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.logging.ConsoleHandler;
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

/**
 * Main : Ruberdriver 실행기.</br>
 * 명령어와 옵션을 받아 Ruberdriver 를 순서대로 실행하거나 병렬로 실행한다.</br>
 *
 * WebDriver 가 thread-safe 하지 않으므로, Ruberdriver는 각자 별도의 Web browser 를 실행한다.</br>
 * option : command-line option object.</br>
 * cfg : config object.</br>
 * driverManager : 단일/병렬로 실행되는 Ruberdriver 가 사용하는 WebDriver 를 관리한다.</br>
 * runnerManager : runnerManager 는 scenario runner 를 관리한다.</br>
 * logger : main logger. logger 는 Main 이 하나, 각 scenario runner 가 하나씩 갖는다.</br>
 *
 * @author johngrib
 */
public class Main {

    static public Options option = null;
    static public Config cfg = null;
    static public WebDriverManager driverManager = new WebDriverManager();
    static public HashMap<String, ScenarioRunner> runnerManager = new HashMap<>();
    static public Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public static void main(String[] args) {

        // Initialize
        option = new Options().build(args);
        cfg = new Config();

        // show version
        if (option.isShowVersion()) {
            System.out.println(Const.VERSION);
            System.exit(0);
        }

        // run ruberdriver
        new Main().setLogger().logDefaultInformation() //
                .runScenarios() //
                .printRunnerStatus() //
                .closeScenarios(); //
    }

    private Main setLogger() {
        logger.setLevel(Level.ALL);
        try {
            String date = LocalDate.now().toString();
            String time = LocalTime.now().toString();
            String form = Main.cfg.getLogPath() + "/ruberdriver--" + date + "--" + time + ".log";

            FileHandler fileTxt = new FileHandler(form);
            ConsoleHandler conTxt = new ConsoleHandler();

            fileTxt.setFormatter(new RuberDriverLoggerFormatter());
            conTxt.setFormatter(new RuberDriverLoggerFormatter(""));

            logger.addHandler(fileTxt);
            logger.setUseParentHandlers(false);
        } catch (SecurityException | IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    private Main logDefaultInformation() {
        logger.log(Level.INFO, "SOURCE JSON : " + option.getSource());
        logger.log(Level.INFO, "EXE OPTIONS : " + String.join(" ", option.getArgs()));
        logger.log(Level.INFO, "INITIATED RUBERDRIVER");
        return this;
    }

    /**
     * scenario 를 실행한다. --async 옵션이 있다면 병렬로 실행하고, 그렇지 않다면 순서대로 실행한다.
     *
     * @return Main
     */
    private Main runScenarios() {
        boolean isAll = option.isAllScenario();
        Collection<String> scenarios = isAll ? cfg.getScenarios().keySet() : option.getScenarioList();

        if (option.isAsync()) {
            runAsync(scenarios);
        } else {
            runSync(scenarios);
        }
        return this;
    }

    /**
     * 각 scenario runner 의 실행 결과를 출력한다.
     *
     * @return Main
     */
    private Main printRunnerStatus() {
        System.out.println("========================");
        for (ScenarioRunner runner : runnerManager.values()) {
            String msg = runner.getFinishStatusString() + " " + runner.getDriverKey();
            runner.getLogger().log(Level.INFO, msg);
            logger.log(Level.INFO, msg);
        }
        return this;
    }

    /**
     * 모든 WebDriver 가 종료되었다면 프로그램을 종료한다.</br>
     * 단, --autoclose 옵션이 없다면 사용자의 ^C 입력을 기다린다.
     */
    private void closeScenarios() {
        int notClosed = getNotClosedDriversCount();

        if (notClosed > 0) {
            printNotClosedDrivers();
            if (option.isAutoQuit()) {
                System.exit(0);
            }
            System.out.println("^C to close all.");
        } else {
            logger.log(Level.INFO, "CLOSING RUBERDRIVER...");
            System.exit(0);
        }
    }

    /**
     * 종료되지 않은 WebDriver 의 수를 리턴한다.
     *
     * @return
     */
    private int getNotClosedDriversCount() {
        System.out.println("========================");
        int cnt = 0;
        Collection<String> keys = driverManager.getDriverList();
        for (String key : keys) {
            WebDriver driver = driverManager.getDriver(key);
            if (driver != null) {
                cnt++;
            }
        }
        return cnt;
    }

    /**
     * 종료되지 않은 WebDriver 의 목록을 출력한다.
     */
    private void printNotClosedDrivers() {
        System.out.println("========================");
        Collection<String> keys = driverManager.getDriverList();
        for (String key : keys) {
            WebDriver driver = driverManager.getDriver(key);
            if (driver != null) {
                logger.log(Level.WARNING, "NOT CLOSED : " + key);
            }
        }
    }

    /**
     * 시나리오 컬렉션을 순서대로 실행한다.
     *
     * @param scenarios
     */
    private void runSync(Collection<String> scenarios) {

        for (String s : scenarios) {
            String s_name = get_scenario_name(s);
            int s_count = get_loop_count(s);
            for (int i = 0; i < s_count; i++) {
                new Ruberdriver(s_name).run();
            }
        }
        scenarios = null;
    }

    /**
     * 시나리오 컬렉션을 병렬로 실행한다.
     *
     * @param scenarios
     */
    private void runAsync(Collection<String> scenarios) {

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

    /**
     * --scenario 옵션의 parameter 로 주어진 각 시나리오의 루프 카운트를 리턴한다.</br>
     * 즉, 다음과 같이 옵션이 주어졌을 때, 5 를 리턴한다.</br>
     *
     * --scenario scenario1_name:5
     *
     * @param scenario
     * @return
     */
    private int get_loop_count(String scenario) {

        String str = scenario.trim();
        if (str.matches("^.+\\:\\s*\\d+\\s*$")) {
            int colon_loc = 1 + str.lastIndexOf(":");
            return Integer.parseInt(str.substring(colon_loc).trim());
        }
        return 1;
    }

    /**
     * --scenario 옵션의 parameter 로 주어진 각 시나리오의 이름을 리턴한다.</br>
     * 즉, 다음과 같이 옵션이 주어졌을 때, scenario1_name 을 리턴한다.</br>
     *
     * --scenario scenario1_name:5
     *
     * @param scenario
     * @return
     */
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
