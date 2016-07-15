package scenario;

import java.util.Scanner;

import org.openqa.selenium.WebDriver;
import command.Command;
import config.Config;

public class ScenarioRunnerDebugMode extends ScenarioRunnerProto {

    private Config cfg;

    public ScenarioRunnerDebugMode(Config cfg) {
        super(cfg);
        this.cfg = cfg;
    }

    @Override
    public WebDriver execute_sentence(String sentence, WebDriver driver, Config cfg) {

        System.out.println(sentence);

        Scanner sc = new Scanner(System.in);
        if (sc.hasNextLine()) {
            sc.nextLine();
        }
        sc.close();
        sc = null;

        String function = super.getFunction(sentence);
        String param = super.getParam(sentence);

        Command command = cfg.getRegister().getCommand(function);

        command.prepare(driver, cfg, param);

        if (command.is_enable()) {
            driver = command.execute();
            command.is_success();
        }

        return driver;
    }

}
