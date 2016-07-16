package controller;

import config.Config;
import model.Item;
import scenariorunner.ScenarioRunner;

public class Ruberdriver extends Thread {

    private Config cfg;

    public Ruberdriver(Config cfg) {
        this.cfg = cfg;
    }

    public void run() {

        String s_name = this.cfg.getRunScenario();
        Item scenario = this.cfg.getScenarios().get(s_name);

        // https://groups.google.com/d/msg/webdriver/cw_awztl-IM/shC3BvJ0gVIJ
        ScenarioRunner exe = new ScenarioRunnerSelector(this.cfg).getScenarioRunner();
        exe.run(scenario);

    }

}
