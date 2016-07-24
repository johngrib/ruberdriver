package controller;

import model.Item;
import scenariorunner.ScenarioRunner;

/**
 * Scenario runner 를 구동하는 Thread.
 * 
 * @author johngrib
 */
public class Ruberdriver extends Thread {

    private String scenario_name;

    public Ruberdriver(String scenario_name) {
        this.scenario_name = scenario_name;
    }

    @Override
    public void run() {

        String s_name = getScenarioName();
        Item scenario = Main.cfg.getScenarios().get(s_name);
        scenario.setName(s_name);

        ScenarioRunner exe = new ScenarioRunnerSelector(scenario).getScenarioRunner();
        Main.runnerManager.put(exe.getDriverKey(), exe);
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
