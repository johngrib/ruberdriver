package command;

import org.openqa.selenium.WebDriver;

public class Click extends CommandProto {

    @Override
    public boolean is_enable() {
        return false;
    }

    @Override
    public WebDriver execute() {
        return driver;
    }

    @Override
    public boolean is_success() {
        return false;
    }
}