package command;

import org.openqa.selenium.WebDriver;

public class Quit extends CommandProto {

    @Override
    public boolean is_enable() {
        return this.driver != null;
    }

    @Override
    public WebDriver execute() {
        this.driver.quit();
        this.driver = null;
        return null;
    }

    @Override
    public boolean is_success() {
        return this.driver == null;
    }

}
