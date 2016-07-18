package command;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import checker.ParameterNotNull;

public class Select extends CommandProto implements ParameterNotNull {

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

}
