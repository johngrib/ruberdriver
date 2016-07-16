package command;

import org.openqa.selenium.WebDriver;

public class Frame extends CommandProto {

    @Override
    public boolean is_enable() {
        return getLastElement().isEnabled();
    }

    @Override
    public WebDriver execute() {

        if("-".equals(this.param)){
            this.driver.switchTo().defaultContent();
        } else {
            this.driver.switchTo().frame(getLastElement());
        }

        return this.driver;
    }

}
