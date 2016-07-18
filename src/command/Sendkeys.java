package command;

import org.openqa.selenium.WebDriver;
import checker.ParameterNotNull;

public class Sendkeys extends CommandProto implements ParameterNotNull {

    @Override
    public boolean is_enable() {
        return getLastElement().isEnabled();
    }

    @Override
    public WebDriver execute() {
        getLastElement().sendKeys(this.param);
        return this.driver;
    }

}
