package controller;

import java.util.HashMap;
import java.util.LinkedList;

import org.junit.Test;
import org.openqa.selenium.WebDriver;

import command.Command;
import config.Config;
import model.Item;

public class Ruberdriver {

    public Ruberdriver() {
    }

    public static void main(String[] args) {

        Config cfg = new Config(args);
        Ruberdriver rd = new Ruberdriver();
        rd.run(cfg);

    }

    public void run(Config cfg) {

        CommandRegister register = new CommandRegister();
        HashMap<String, Item> items = cfg.getItems();

        String s_name = cfg.getRunScenario();
        Item scenario = cfg.getScenarios().get(s_name);

        LinkedList<String> scenes = scenario.getList();

        WebDriver driver = null;

        for (String s : scenes) {

            Item item = items.get(s);
            LinkedList<String> user_strings = item.getList();

            for (String sentence : user_strings) {
                System.out.println(sentence);

                String function = sentence.replaceFirst("\\s.*", "");
                String param = sentence.replaceFirst("^[A-Za-z]+\\s", "");

                Command command = register.getCommand(function);

                command.prepare(driver, cfg, param);

                if (command.is_enable()) {
                    driver = command.execute();
                    command.is_success();
                }
            }
        }
    }

    @Test
    public void mainTest() {
        String[] args = { "--scenario", "login_test" };
        Ruberdriver.main(args);
    }
}
