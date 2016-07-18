package command;

import org.openqa.selenium.WebDriver;
import checker.ParameterNumber;

public class Wait extends CommandProto implements ParameterNumber {

    @Override
    public boolean is_enable() {
        return true;
    }

    @Override
    public WebDriver execute() {
        long delay = Long.parseLong(this.param);

        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return driver;
    }

    @Override
    public boolean is_success() {
        return true;
    }

}
