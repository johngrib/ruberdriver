package controller;

import config.Config;
import scenario.Scenario;
import scenario.ScenarioRunnerDebugMode;
import scenario.ScenarioRunnerProto;

public class ScenarioRunnerSelector {

    private Config cfg;

    public ScenarioRunnerSelector(Config cfg) {
        super();
        this.cfg = cfg;
    }

    public Scenario getScenarioRunner() {
        if (this.cfg.isDebugMode()) {
            return new ScenarioRunnerDebugMode(this.cfg);
        }
        return new ScenarioRunnerProto(this.cfg);
    }

}
