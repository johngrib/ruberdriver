package command;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class Mouseover extends CommandProto {

    @Override
    public boolean is_enable() {
        return this.cfg.getLastElement().isDisplayed();
    }

    @Override
    public WebDriver execute() {

        WebElement element = cfg.getLastElement();
        do_mouse_over(element);

        return this.driver;
    }


    private void do_mouse_over(WebElement element) {
        Actions action = new Actions(this.driver);
        action.moveToElement(element).perform();;
    }
}
