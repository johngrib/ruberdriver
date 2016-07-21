package scenariorunner;

import org.openqa.selenium.WebDriver;
import model.Item;
import model.ScenarioSubItem;

public interface ScenarioRunner {
    public void run(Item scenario);

    public WebDriver execute_sentence(String sentence, WebDriver driver);

    public ScenarioSubItem getLocalItem();

    public String getName();

    public String getDriverKey();
}
