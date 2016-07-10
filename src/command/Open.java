package command;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Open extends CommandProto {

    @Override
    public boolean is_enable() {
        return true;
    }

    @Override
    public WebDriver execute() {

        switch (this.param) {
        case "chrome":
            this.driver = new ChromeDriver();
            break;
        case "safari":
            this.driver = new SafariDriver();
            break;
        case "firefox":
            this.driver = new FirefoxDriver();
            break;
        default:
            this.driver = new ChromeDriver();
            break;
        }
        
        this.wait = new WebDriverWait(this.driver, 30);
        return this.driver;
    }

    @Override
    public boolean is_success() {
        return this.driver != null;
    }

}
