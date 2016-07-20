package scenariorunner;

import java.util.HashMap;
import java.util.LinkedList;
import org.openqa.selenium.WebDriver;
import command.Command;
import config.Const;
import controller.CommandRegister;
import controller.Main;
import lombok.Getter;
import lombok.Setter;
import model.Item;
import model.ScenarioSubItem;

public class ScenarioRunnerProto implements ScenarioRunner {

    @Getter
    private ScenarioSubItem localItem;
    protected CommandRegister register;

    @Setter
    private String name;

    public ScenarioRunnerProto(String name) {
        super();
        this.localItem = new ScenarioSubItem();
        this.register = new CommandRegister();
        this.name = name;
    }

    protected void prepare() {
    }

    protected void end() {
    }

    @Override
    public void run(Item scenario) {

        this.prepare();

        HashMap<String, Item> items = Main.cfg.getItems();
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
                    driver = execute_sentence(sentence, driver);
                }
            }
        }
        this.end();
    }

    @Override
    public WebDriver execute_sentence(String sentence, WebDriver driver) {
        this.printScriptSentences(sentence);

        String function = getFunction(sentence);
        String param = getParam(sentence);

        Command command = this.register.getCommand(function);

        command.prepare(driver, this, sentence, param);

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
        return sentence.replaceFirst("^[A-Za-z]+\\s*", "");
    }

    protected void printScriptSentences(String sentence) {
        if (Main.option.isPrintScriptSentences()) {
            System.out.println(sentence);
        }
    }

}
