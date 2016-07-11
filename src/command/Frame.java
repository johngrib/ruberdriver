package command;

import org.openqa.selenium.WebDriver;

public class Frame extends CommandProto {

    @Override
    public boolean is_enable() {
        return this.cfg.getLastElement().isEnabled();
    }

    @Override
    public WebDriver execute() {

        if("-".equals(this.param)){
            this.driver.switchTo().defaultContent();
        } else {
            this.driver.switchTo().frame(cfg.getLastElement());
        }

        return this.driver;
    }

}
