package command;

import org.openqa.selenium.WebDriver;

public class Close extends CommandProto {

    @Override
    public boolean is_enable() {
        return this.driver != null;
    }

    @Override
    public WebDriver execute() {
        this.driver.close();
        this.driver = null;
        return null;
    }

    @Override
    public boolean is_success() {
        return this.driver == null;
    }

}
