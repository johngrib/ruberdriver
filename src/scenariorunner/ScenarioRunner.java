package scenariorunner;

import java.util.logging.Logger;

import org.openqa.selenium.WebDriver;
import model.Item;
import model.ScenarioSubItem;

public interface ScenarioRunner {
    public void run(Item scenario);

    public WebDriver execute_sentence(String sentence, WebDriver driver);

    public ScenarioSubItem getLocalItem();

    public String getName();

    public String getDriverKey();

    public void stop();

    public Logger getLogger();

    public String getProgressString();

    public String getFinishStatusString();

    public boolean isPassed();
}
