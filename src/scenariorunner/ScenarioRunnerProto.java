package scenariorunner;

import java.util.HashMap;
import java.util.LinkedList;
import org.openqa.selenium.WebDriver;
import command.Command;
import config.Config;
import config.Const;
import model.Item;

public class ScenarioRunnerProto implements ScenarioRunner {

    private Config cfg;

    public ScenarioRunnerProto(Config cfg) {
        super();
        this.cfg = cfg;
    }

    protected void prepare() {
    }

    protected void end() {
    }

    @Override
    public void run(Item scenario) {

        this.prepare();

        HashMap<String, Item> items = cfg.getItems();
        LinkedList<String> scenes = scenario.getList();
        WebDriver driver = null;

        for (String s : scenes) {
            Item item = items.get(s);
            LinkedList<String> user_strings = item.getList();

            for (String sentence : user_strings) {
                if (sentence.startsWith(Const.COMMENT_FORCE_QUIT)) {
                    this.end();
                    return;
                } else {
                    driver = execute_sentence(sentence, driver, cfg);
                }
            }
        }
        this.end();
    }

    @Override
    public WebDriver execute_sentence(String sentence, WebDriver driver, Config cfg) {
        this.printScriptSentences(sentence);

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

    protected void printScriptSentences(String sentence) {
        if (this.cfg.isPrintScriptSentences()) {
            System.out.println(sentence);
        }
    }
}
