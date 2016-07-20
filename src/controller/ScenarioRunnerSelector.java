package controller;

import scenariorunner.ScenarioRunner;
import scenariorunner.ScenarioRunnerInteractiveMode;
import scenariorunner.ScenarioRunnerProto;
import scenariorunner.ScenarioRunnerSyntaxCheck;

public class ScenarioRunnerSelector {

    private String scenario_name;

    public ScenarioRunnerSelector(String scenario_name) {
        super();
        this.scenario_name = scenario_name;
    }

    public ScenarioRunner getScenarioRunner() {

        if (Main.option.isSyntaxCheck()){
            return new ScenarioRunnerSyntaxCheck(this.scenario_name);
        } else if (Main.option.isInteractiveMode()) {
            return new ScenarioRunnerInteractiveMode(this.scenario_name);
        }
        return new ScenarioRunnerProto(this.scenario_name);
    }

}
