package command;

import org.openqa.selenium.WebDriver;

public class Alert extends CommandProto {

    @Override
    public boolean is_enable() {
        return true;
    }

    @Override
    public WebDriver execute() {

        org.openqa.selenium.Alert alert = this.driver.switchTo().alert();
 
        if("accept".equals(this.param)){
            alert.accept();
        } else {
            alert.dismiss();
        }
        
        this.driver.switchTo().defaultContent();

        return this.driver;
    }

}