package controller;

import config.Config;
import model.Item;
import scenariorunner.ScenarioRunner;

public class Ruberdriver extends Thread {

    private Config cfg;
    private String scenario_name;

    public Ruberdriver(Config cfg) {
        this.cfg = cfg;
    }

    public Ruberdriver(Config cfg, String scenario_name) {
        this.cfg = cfg;
        this.scenario_name = scenario_name;
    }

    public void run() {

        Item scenario = this.cfg.getScenarios().get(getScenarioName());

        // https://groups.google.com/d/msg/webdriver/cw_awztl-IM/shC3BvJ0gVIJ
        ScenarioRunner exe = new ScenarioRunnerSelector(this.cfg).getScenarioRunner();
        exe.run(scenario);

    }

    private String getScenarioName() {

        if (this.scenario_name != null) {
            return this.scenario_name;
        } else {
            return this.cfg.getOption().getRunScenario();
        }
    }

}
