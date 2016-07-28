package command;

import org.openqa.selenium.WebDriver;

public class Refresh extends CommandProto {

    @Override
    public WebDriver execute() {

        this.driver.navigate().refresh();

        return this.driver;
    }

}
