package controller;

import java.util.HashMap;
import java.util.LinkedList;

import org.openqa.selenium.WebDriver;

import command.Command;
import config.Config;
import config.Const;
import model.Item;

public class ScenarioExe implements Runnable {

    private Item scenario;
    private Config cfg;

    public ScenarioExe(Item scenario, Config cfg) {
        super();
        this.scenario = scenario;
        this.cfg = cfg;
    }

    @Override
    public void run() {
        execute();
    }

    public void execute() {

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
                    driver = execute(sentence, driver, cfg);
                }
            }
        }
    }

    private WebDriver execute(String sentence, WebDriver driver, Config cfg) {
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

    private String getFunction(String sentence) {
        return sentence.replaceFirst("\\s.*", "");
    }

    private String getParam(String sentence) {
        return sentence.replaceFirst("^[A-Za-z]+\\s", "");
    }

}
