package command;

import org.openqa.selenium.WebDriver;

public class Forward extends CommandProto {

    @Override
    public WebDriver execute() {

        this.driver.navigate().forward();

        return this.driver;
    }

}
