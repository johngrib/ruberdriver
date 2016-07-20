package controller;

import model.Item;
import scenariorunner.ScenarioRunner;

public class Ruberdriver extends Thread {

    private String scenario_name;

    public Ruberdriver(String scenario_name) {
        this.scenario_name = scenario_name;
    }

    public void run() {

        Item scenario = Main.cfg.getScenarios().get(getScenarioName());

        // https://groups.google.com/d/msg/webdriver/cw_awztl-IM/shC3BvJ0gVIJ
        ScenarioRunner exe = new ScenarioRunnerSelector(this.scenario_name).getScenarioRunner();
        exe.run(scenario);

    }

    private String getScenarioName() {

        if (this.scenario_name != null) {
            return this.scenario_name;
        } else {
            return Main.option.getRunScenario();
        }
    }

}
