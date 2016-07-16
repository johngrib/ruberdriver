package controller;

import org.junit.Test;
import config.Config;
import model.Item;
import scenariorunner.ScenarioRunner;

public class Ruberdriver {

    public Ruberdriver() {
    }

    public static void main(String[] args) {

        Config cfg = new Config(args);
        Ruberdriver rd = new Ruberdriver();
        rd.run(cfg);
    }

    public void run(Config cfg) {

        String s_name = cfg.getRunScenario();
        Item scenario = cfg.getScenarios().get(s_name);

        // https://groups.google.com/d/msg/webdriver/cw_awztl-IM/shC3BvJ0gVIJ
        ScenarioRunner exe = new ScenarioRunnerSelector(cfg).getScenarioRunner();
        exe.run(scenario);

    }

    @Test
    public void mainTest() {
        String[] args = { "--source", "test.json", "--scenario", "purchase_test", "-d" };
        //String[] args = { "--source", "test.json", "--scenario", "login_test" };
        //String[] args = { "--scenario", "go_test" };
        Ruberdriver.main(args);
    }
}
