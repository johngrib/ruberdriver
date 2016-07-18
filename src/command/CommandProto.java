package command;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Wait;
import checker.ParameterNotNull;
import model.ScenarioSubItem;
import scenariorunner.ScenarioRunner;

public class CommandProto implements Command {

    protected WebDriver driver;
    protected Wait<WebDriver> wait;
    protected ScenarioRunner runner;
    protected String sentence;
    protected String param;
    private String name;

    @Override
    public String getName() {
        if (this.name == null) {
            this.name = this.getClass().getName().replaceFirst("[A-Za-z]+\\.", "");
        }
        return this.name;
    }

    @Override
    public WebDriver prepare(WebDriver driver, ScenarioRunner runner, String sentence, String param) {
        this.driver = driver;
        this.runner = runner;
        this.sentence = sentence;
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

    @Override
    public boolean is_valid_syntax() {
        if (this instanceof ParameterNotNull) {
            if (param == null || param.trim().isEmpty()) {
                String msg = String.format("[%s] parameter should be not null.", this.getName());
                System.out.println(msg);
                return false;
            }
        }
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

    public String getFunction(String sentence) {
        return sentence.replaceFirst("\\s.*", "");
    }

    public String getParam(String sentence) {
        return sentence.replaceFirst("^[A-Za-z]+\\s*", "");
    }

}
