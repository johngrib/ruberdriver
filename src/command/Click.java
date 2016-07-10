package command;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class Click extends CommandProto {

    @Override
    public boolean is_enable() {
        return this.cfg.getLastElement().isEnabled();
    }

    @Override
    public WebDriver execute() {

        int count = get_count(this.param);

        WebElement element = this.cfg.getLastElement();

        switch (count) {
        case 1:
            do_click(element);
            break;
        case 2:
            do_double_click(element);
            break;
        default:
            break;
        }

        return this.driver;
    }

    private void do_click(WebElement element) {
        element.click();
    }

    private void do_double_click(WebElement element) {
        Actions action = new Actions(this.driver);
        action.doubleClick(element).perform();
    }
    
    private int get_count(String param){
        int count = 1;
        if (param.matches("^\\d+$")) {
            count = Integer.parseInt(param);
        }
        return count;
    }
}
