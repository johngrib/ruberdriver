package command;


import org.openqa.selenium.WebDriver;

public class Sendkeys extends CommandProto {

    @Override
    public boolean is_enable() {
        return this.cfg.getLastElement().isEnabled();
    }

    @Override
    public WebDriver execute() {
        this.cfg.getLastElement().sendKeys(this.param);
        return this.driver;
    }

}
