package command;

import org.openqa.selenium.WebDriver;

public class Echo extends CommandProto {

    @Override
    public WebDriver execute() {

        System.out.println(this.param);

        return this.driver;
    }

}
