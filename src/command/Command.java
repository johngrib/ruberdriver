package command;

import org.openqa.selenium.WebDriver;
import scenariorunner.ScenarioRunner;

public interface Command {

    public abstract WebDriver prepare(WebDriver driver, ScenarioRunner runner, String sentence, String param);

    public abstract boolean is_enable();

    public abstract WebDriver execute();

    public abstract boolean is_success();

    public abstract String getName();

    public abstract boolean is_valid_syntax();
}
