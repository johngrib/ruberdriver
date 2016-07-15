package scenario;

import java.util.HashMap;
import java.util.LinkedList;
import org.openqa.selenium.WebDriver;
import command.Command;
import config.Config;
import config.Const;
import model.Item;

public class ScenarioRunnerProto implements Scenario {

    private Config cfg;

    public ScenarioRunnerProto(Config cfg) {
        super();
        this.cfg = cfg;
    }

    @Override
    public void run(Item scenario) {

        HashMap<String, Item> items = cfg.getItems();
        LinkedList<String> scenes = scenario.getList();
        WebDriver driver = null;

        for (String s : scenes) {
            Item item = items.get(s);
            LinkedList<String> user_strings = item.getList();

            for (String sentence : user_strings) {
                if (sentence.startsWith(Const.COMMENT_FORCE_QUIT)) {
                    return;
                } else {
                    driver = execute_sentence(sentence, driver, cfg);
                }
            }
        }
    }

    @Override
    public WebDriver execute_sentence(String sentence, WebDriver driver, Config cfg) {
        System.out.println(sentence);

        String function = getFunction(sentence);
        String param = getParam(sentence);

        Command command = cfg.getRegister().getCommand(function);

        command.prepare(driver, cfg, param);

        if (command.is_enable()) {
            driver = command.execute();
            command.is_success();
        }
        return driver;
    }

    protected String getFunction(String sentence) {
        return sentence.replaceFirst("\\s.*", "");
    }

    protected String getParam(String sentence) {
        return sentence.replaceFirst("^[A-Za-z]+\\s", "");
    }
}
