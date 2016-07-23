package controller;

import model.Item;
import scenariorunner.ScenarioRunner;

public class Ruberdriver extends Thread {

    private String scenario_name;

    public Ruberdriver(String scenario_name) {
        this.scenario_name = scenario_name;
    }

    public void run() {

        String s_name = getScenarioName();
        Item scenario = Main.cfg.getScenarios().get(s_name);
        scenario.setName(s_name);

        // https://groups.google.com/d/msg/webdriver/cw_awztl-IM/shC3BvJ0gVIJ
        ScenarioRunner exe = new ScenarioRunnerSelector(scenario).getScenarioRunner();
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
