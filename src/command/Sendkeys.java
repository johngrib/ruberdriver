package command;


import org.openqa.selenium.WebDriver;

public class Sendkeys extends CommandProto {

    @Override
    public boolean is_enable() {
        return getLastElement().isEnabled();
    }

    @Override
    public WebDriver execute() {
        getLastElement().sendKeys(this.param);
        return this.driver;
    }

}
