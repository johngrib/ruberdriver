package controller;

import config.Config;
import scenariorunner.ScenarioRunner;
import scenariorunner.ScenarioRunnerDebugMode;
import scenariorunner.ScenarioRunnerProto;
import scenariorunner.ScenarioRunnerSyntaxCheck;

public class ScenarioRunnerSelector {

    private Config cfg;

    public ScenarioRunnerSelector(Config cfg) {
        super();
        this.cfg = cfg;
    }

    public ScenarioRunner getScenarioRunner() {
        if (this.cfg.getOption().isSyntaxCheck()){
            return new ScenarioRunnerSyntaxCheck(cfg);
        } else if (this.cfg.getOption().isDebugMode()) {
            return new ScenarioRunnerDebugMode(this.cfg);
        }
        return new ScenarioRunnerProto(this.cfg);
    }

}
