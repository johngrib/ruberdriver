package command;

import org.openqa.selenium.WebDriver;

import config.Config;
import scenariorunner.ScenarioRunner;

public interface Command {

    public abstract WebDriver prepare(WebDriver driver, Config cfg, String param, ScenarioRunner runner);

    public abstract boolean is_enable();

    public abstract WebDriver execute();

    public abstract boolean is_success();

    public abstract String getName();

    public abstract boolean is_valid_syntax();
}
