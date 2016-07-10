package controller;

import org.junit.Test;
import config.Config;
import model.Item;

public class Ruberdriver {

    public Ruberdriver() {
    }

    public static void main(String[] args) {

        Config cfg = new Config(args);
        Ruberdriver rd = new Ruberdriver();
        rd.run(cfg);
        System.exit(0);
    }

    public void run(Config cfg) {

        String s_name = cfg.getRunScenario();
        Item scenario = cfg.getScenarios().get(s_name);

        // https://groups.google.com/d/msg/webdriver/cw_awztl-IM/shC3BvJ0gVIJ
        ScenarioExe exe = new ScenarioExe(scenario, cfg);
        exe.execute();

    }

    @Test
    public void mainTest() {
        String[] args = { "--source", "test.json", "--scenario", "purchase_test" };
        //String[] args = { "--source", "test.json", "--scenario", "login_test" };
        //String[] args = { "--scenario", "go_test" };
        Ruberdriver.main(args);
    }
}
