package scenariorunner;

import org.openqa.selenium.WebDriver;
import config.Config;
import model.Item;
import model.ScenarioSubItem;

public interface ScenarioRunner {
    public void run(Item scenario);

    public WebDriver execute_sentence(String sentence, WebDriver driver, Config cfg);

    public ScenarioSubItem getLocalItem();

    public Config getCfg();
}
