package command;

import org.openqa.selenium.WebDriver;

public class Maximize extends CommandProto {

    @Override
    public WebDriver execute() {

        this.driver.manage().window().maximize();

        return this.driver;
    }

}
