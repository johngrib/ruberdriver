package command;

import org.openqa.selenium.WebDriver;

public class Find extends CommandProto {

    @Override
    public WebDriver execute() {
        return driver;
    }

}
