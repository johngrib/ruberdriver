package scenariorunner;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.logging.ConsoleHandler;
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

    private int sceneMax = 0;

    private int sceneCnt = 0;

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

        setLogger();

    }

    private void setLogger() {
        logger = Logger.getLogger(this.driverKey);

        try {
            logger.setLevel(Level.ALL);

            FileHandler fileTxt = new FileHandler(Main.cfg.getLogPath() + "/" + this.driverKey + ".log");
            ConsoleHandler conTxt = new ConsoleHandler();

            fileTxt.setFormatter(new RuberDriverLoggerFormatter());
            conTxt.setFormatter(new RuberDriverLoggerFormatter());

            logger.addHandler(fileTxt);
            logger.addHandler(conTxt);
            logger.setUseParentHandlers(false);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
        this.isRun = false;
    }

    @Override
    public String getProgressString() {
        String progress = String.format("%04d/%04d", this.sceneCnt, this.sceneMax);
        return progress;
    }

    @Override
    public String getFinishStatusString() {
        String is_pass = (this.sceneCnt == this.sceneMax) ? "SUCCESS" : "FAILED";
        String progress = String.format("%s %04d/%04d", is_pass, this.sceneCnt, this.sceneMax);
        return progress;
    }

    @Override
    public boolean isPassed() {
        return this.sceneCnt == this.sceneMax;
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

        this.sceneCnt = 0;
        this.sceneMax = getSenteceCnt(scenario);

        logger.log(Level.INFO, "SOURCE JSON : " + Main.option.getSource());
        logger.log(Level.INFO, "EXE OPTIONS : " + String.join(" ", Main.option.getArgs()));
        logger.log(Level.INFO, "STARTED " + this.getDriverKey());

        for (String s : scenes) {
            Item item = items.get(s);
            LinkedList<String> user_strings = item.getList();

            for (String sentence : user_strings) {
                if (!this.isRun || sentence.startsWith(Const.COMMENT_FORCE_QUIT)) {
                    this.end();
                    return;
                } else {
                    this.sceneCnt++;
                    logger.log(Level.INFO, this.getProgressString() + " " + sentence);
                    driver = execute_sentence(sentence, driver);
                }
            }
        }
        this.end();
    }

    private int getSenteceCnt(Item scenario) {
        HashMap<String, Item> items = Main.cfg.getItems();
        LinkedList<String> scenes = scenario.getList();

        int cnt = 0;

        for (String s : scenes) {
            Item item = items.get(s);
            cnt += item.getList().size();
        }
        return cnt;
    }

    @Override
    public WebDriver execute_sentence(String sentence, WebDriver driver) {

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

}
