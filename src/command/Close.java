package command;

import org.openqa.selenium.WebDriver;

import controller.Main;

public class Close extends CommandProto {

    @Override
    public boolean is_enable() {
        return this.driver != null;
    }

    @Override
    public WebDriver execute() {
        String key = this.runner.getDriverKey();
        Main.driverManager.closeDriver(key);
        return null;
    }

    @Override
    public boolean is_success() {
        return this.driver == null;
    }

}
