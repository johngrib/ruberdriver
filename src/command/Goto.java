package command;

import org.openqa.selenium.WebDriver;

public class Goto extends CommandProto {

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
