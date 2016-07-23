package scenariorunner;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openqa.selenium.WebDriver;
import command.Command;
import config.Const;
import controller.CommandRegister;
import controller.Main;
import lombok.Getter;
import model.Item;
import model.ScenarioSubItem;
import util.RuberDriverLoggerFormatter;

public class ScenarioRunnerProto implements ScenarioRunner {

    @Getter
    private ScenarioSubItem localItem;
    protected CommandRegister register;

    @Getter
    private String name;

    @Getter
    private String driverKey;

    private boolean isRun;

    @Getter
    private Logger logger;

    public ScenarioRunnerProto(String name) {
        super();
        this.localItem = new ScenarioSubItem();
        this.register = new CommandRegister();
        this.name = name;
        this.isRun = true;

        LocalDate today = LocalDate.now();
        LocalTime time = LocalTime.now();

        this.driverKey = String.format("[%s]--%s--%s--%s", name, today, time, this.hashCode());

        Runtime.getRuntime().addShutdownHook(new controller.Shutdown(this));

        logger = Logger.getLogger(this.driverKey);

        try {
            logger.setLevel(Level.ALL);
            FileHandler fileTxt = new FileHandler(Main.cfg.getLogPath() + "/" + this.driverKey + ".log");
            fileTxt.setFormatter(new RuberDriverLoggerFormatter());
            logger.addHandler(fileTxt);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void stop() {
        this.isRun = false;
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
                if (!this.isRun || sentence.startsWith(Const.COMMENT_FORCE_QUIT)) {
                    this.end();
                    return;
                } else {
                    logger.info(sentence);
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
