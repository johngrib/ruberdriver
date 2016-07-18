package command;

import org.openqa.selenium.WebDriver;

import checker.ParameterNotNull;

public class Goto extends CommandProto implements ParameterNotNull {

    @Override
    public boolean is_enable() {
        return true;
    }

    @Override
    public WebDriver execute() {
        this.driver.get(param);
        return this.driver;
    }

    @Override
    public boolean is_success() {
        return true;
    }

}
