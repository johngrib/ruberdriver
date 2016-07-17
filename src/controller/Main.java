package controller;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import config.Config;
import config.Options;

public class Main {

    public static void main(String[] args) {

        Main main = new Main();
        Options option = new Options().build(args);
        Config cfg = new Config(option);

        if (option.isAsync()) {

            boolean isAll = option.isAllScenario();
            Collection<String> scenarios = isAll ? cfg.getScenarios().keySet() : option.getScenarioList();
            main.runAsync(cfg, option, scenarios);

        } else {
            main.runSync(cfg, option);
        }

        main = null;
        option = null;
        cfg = null;
    }

    public void runSync(Config cfg, Options option) {
        List<String> scenarios = option.getScenarioList();

        for (String s : scenarios) {
            String s_name = get_scenario_name(s);
            int s_count = get_loop_count(s);
            for (int i = 0; i < s_count; i++) {
                new Ruberdriver(cfg, s_name).run();
            }
        }
        scenarios = null;
    }

    public void runAsync(Config cfg, Options option, Collection<String> scenarios) {

        LinkedList<Thread> threads = new LinkedList<>();

        for (String s : scenarios) {

            String s_name = get_scenario_name(s);
            int s_count = get_loop_count(s);

            for (int i = 0; i < s_count; i++) {
                Thread rd = new Ruberdriver(cfg, s_name);
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

    @Test
    public void mainTest() {
        String[] args = { //
                "--source", "test.json", //
                "--scenario", "purchase_test:2,login_test", "-a" //
                //"--scenario", "purchase_test", "-a", "-l" //
                // "-c", "purchase_test", "-d" //
                // "-c", "purchase_test" //
                // "-c", "login_test:2,purchase_test:2" //
        };
        Main.main(args);
    }
}
