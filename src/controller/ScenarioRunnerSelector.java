package controller;

import config.Config;
import scenariorunner.ScenarioRunner;
import scenariorunner.ScenarioRunnerDebugMode;
import scenariorunner.ScenarioRunnerProto;

public class ScenarioRunnerSelector {

    private Config cfg;

    public ScenarioRunnerSelector(Config cfg) {
        super();
        this.cfg = cfg;
    }

    public ScenarioRunner getScenarioRunner() {
        if (this.cfg.getOption().isDebugMode()) {
            return new ScenarioRunnerDebugMode(this.cfg);
        }
        return new ScenarioRunnerProto(this.cfg);
    }

}
