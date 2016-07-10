package command;

import org.openqa.selenium.WebDriver;

import config.Config;

public interface Command {

    public abstract WebDriver prepare(WebDriver driver, Config cfg, String param);

    public abstract boolean is_enable();

    public abstract WebDriver execute();

    public abstract boolean is_success();
}
