package controller;

import config.Config;
import scenario.Scenario;
import scenario.ScenarioRunnerProto;

public class ScenarioRunnerSelector {

    private Config cfg;

    public ScenarioRunnerSelector(Config cfg) {
        super();
        this.cfg = cfg;
    }
    
    public Scenario getScenarioRunner(){
        return new ScenarioRunnerProto(this.cfg);
    }

}
