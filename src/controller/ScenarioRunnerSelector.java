package controller;

import model.Item;
import scenariorunner.ScenarioRunner;
import scenariorunner.ScenarioRunnerInteractiveMode;
import scenariorunner.ScenarioRunnerProto;
import scenariorunner.ScenarioRunnerSyntaxCheck;

/**
 * 사용자의 option 에 맞는 Scenario runner 를 선택해 준다.
 * @author johngrib
 *
 */
public class ScenarioRunnerSelector {

    private Item scenario;

    public ScenarioRunnerSelector(Item scenario) {
        super();
        this.scenario = scenario;
    }

    public ScenarioRunner getScenarioRunner() {

        if (Main.option.isSyntaxCheck()){
            return new ScenarioRunnerSyntaxCheck(this.scenario.getName());
        } else if (Main.option.isInteractiveMode()) {
            return new ScenarioRunnerInteractiveMode(this.scenario.getName());
        }
        return new ScenarioRunnerProto(this.scenario.getName());
    }

}
