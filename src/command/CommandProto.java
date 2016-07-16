package command;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Wait;
import config.Config;
import model.ScenarioSubItem;
import scenariorunner.ScenarioRunner;

public class CommandProto implements Command {

    protected WebDriver driver;
    protected Wait<WebDriver> wait;
    protected Config cfg;
    protected String param;
    protected ScenarioRunner runner;

    @Override
    public WebDriver prepare(WebDriver driver, Config cfg, String param, ScenarioRunner runner) {
        this.driver = driver;
        this.cfg = cfg;
        this.param = param;
        this.runner = runner;
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

    public ScenarioSubItem getSubItem() {
        return this.runner.getLocalItem();
    }

    public WebElement getLastElement() {
        return this.getSubItem().getLastElement();
    }

    public void setLastElement(WebElement element) {
        this.getSubItem().setLastElement(element);
    }
}
