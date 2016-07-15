package scenario;

import org.openqa.selenium.WebDriver;
import config.Config;
import model.Item;

public interface Scenario {
    public void run(Item scenario);
    public WebDriver execute_sentence(String sentence, WebDriver driver, Config cfg);
}
