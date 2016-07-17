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
    private String name;

    @Override
    public String getName() {
        if (this.name == null) {
            this.name = this.getClass().getName().replaceFirst("[A-Za-z]+\\.", "");
        }
        return this.name;
    }

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

    @Override
    public boolean is_valid_syntax() {
        return false;
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

    public String getFunction(String sentence) {
        return sentence.replaceFirst("\\s.*", "");
    }

    public String getParam(String sentence) {
        return sentence.replaceFirst("^[A-Za-z]+\\s", "");
    }


}
