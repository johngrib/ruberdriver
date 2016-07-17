package controller;

import java.util.Iterator;
import java.util.LinkedList;
import org.junit.Assert;
import org.junit.Test;
import config.Config;
import config.Options;

public class Main {

    public static void main(String[] args) {

        Main main = new Main();
        Options option = new Options().build(args);
        Config cfg = new Config(option);

        if (option.isAsync() && option.isAllScenario()) {
            main.runAsyncAll(cfg, option);
        } else if (option.isAsync()) {
            main.runAsync(cfg, option);
        } else {
            new Ruberdriver(cfg).run();
            System.exit(0);
        }
    }

    public void runAsyncAll(Config cfg, Options option) {

        LinkedList<Thread> threads = new LinkedList<>();

        Iterator<String> it = cfg.getScenarios().keySet().iterator();

        while (it.hasNext()) {
            String scenario = (String) it.next();

            Thread rd = new Ruberdriver(cfg, scenario);
            threads.add(rd);
            rd.start();
        }

        Iterator<Thread> tit = threads.iterator();
        try {
            while (tit.hasNext()) {
                Thread thread = (Thread) tit.next();
                thread.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return;
    }

    public void runAsync(Config cfg, Options option) {

        LinkedList<Thread> threads = new LinkedList<>();

        String[] scenarios = option.getScenarioArray();

        for (String s : scenarios) {

            String s_name = get_scenario_name(s);
            int s_count = get_loop_count(s);

            for (int i = 0; i < s_count; i++) {
                Thread rd = new Ruberdriver(cfg, s_name);
                threads.add(rd);
                rd.start();
            }
        }

        Iterator<Thread> it = threads.iterator();
        try {
            while (it.hasNext()) {
                Thread thread = (Thread) it.next();
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

    @Test
    public void mainTest() {
        String[] args = { "--source", "test.json", "--scenario", "purchase_test,login_test", "-a" };
        // String[] args = { "--source", "test.json", "--scenario", "purchase_test", "-a", "-l" };
        // String[] args = { "--source", "test.json", "--scenario", "purchase_test", "-d" };
        // String[] args = { "--source", "test.json", "--scenario", "purchase_test" };
        // String[] args = { "--source", "test.json", "--scenario", "login_test" };
        // String[] args = { "--scenario", "go_test" };
        Main.main(args);
    }
}
