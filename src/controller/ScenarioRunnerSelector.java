package controller;

import config.Config;
import scenariorunner.ScenarioRunner;
import scenariorunner.ScenarioRunnerInteractiveMode;
import scenariorunner.ScenarioRunnerProto;
import scenariorunner.ScenarioRunnerSyntaxCheck;

public class ScenarioRunnerSelector {

    private Config cfg;
    private String scenario_name;

    public ScenarioRunnerSelector(Config cfg, String scenario_name) {
        super();
        this.cfg = cfg;
        this.scenario_name = scenario_name;
    }

    public ScenarioRunner getScenarioRunner() {
        if (Main.option.isSyntaxCheck()){
            return new ScenarioRunnerSyntaxCheck(this.cfg, this.scenario_name);
        } else if (Main.option.isInteractiveMode()) {
            return new ScenarioRunnerInteractiveMode(this.cfg, this.scenario_name);
        }
        return new ScenarioRunnerProto(this.cfg, this.scenario_name);
    }

}
