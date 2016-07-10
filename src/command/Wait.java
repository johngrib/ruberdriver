package command;

import org.openqa.selenium.WebDriver;

public class Wait extends CommandProto {

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

        System.out.println("test delayed");
        return driver;
    }

    @Override
    public boolean is_success() {
        return true;
    }

}
