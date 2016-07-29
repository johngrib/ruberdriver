package command;

import org.openqa.selenium.WebDriver;

public class Back extends CommandProto {

    @Override
    public WebDriver execute() {

        this.driver.navigate().back();

        return this.driver;
    }

}
