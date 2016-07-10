package command;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Wait;

import config.Config;

public class CommandProto implements Command {

    protected WebDriver driver;
    protected Wait<WebDriver> wait;
    protected Config cfg;
    protected String param;

    @Override
    public WebDriver prepare(WebDriver driver, Config cfg, String param) {
        this.driver = driver;
        this.cfg = cfg;
        this.param = param;
        return this.driver;
    }

    @Override
    public boolean is_enable() {
        return true;
    }

    @Override
    public WebDriver execute() {
        return driver;
    }

    @Override
    public boolean is_success() {
        return true;
    }
}
