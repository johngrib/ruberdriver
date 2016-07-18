package command;

import java.util.Set;
import java.util.TreeSet;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import checker.ParameterDefinedMethod;
import checker.ParameterNotNull;

public class Select extends CommandProto implements ParameterNotNull, ParameterDefinedMethod {

    private Set<String> param_methods;

    @Override
    public boolean is_enable() {
        return getLastElement().isEnabled();
    }

    @Override
    public WebDriver execute() {

        WebElement element = getLastElement();

        String function = getFunction(this.param);
        String query = getParam(this.param);

        org.openqa.selenium.support.ui.Select select = new org.openqa.selenium.support.ui.Select(element);

        switch (function) {
        case "index":
            select.selectByIndex(Integer.parseInt(query));
            break;
        case "value":
            select.selectByValue(query);
            break;
        case "text":
            select.selectByVisibleText(query);
        default:
            break;
        }

        return this.driver;
    }

    @Override
    public Set<String> get_defined_param_methods() {
        if (this.param_methods == null) {
            this.param_methods = new TreeSet<>();
            this.param_methods.add("index");
            this.param_methods.add("value");
            this.param_methods.add("text");
        }
        return this.param_methods;
    }

}
