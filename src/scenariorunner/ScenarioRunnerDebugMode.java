package scenariorunner;

import java.util.Scanner;

import org.openqa.selenium.WebDriver;
import command.Command;
import config.Config;

public class ScenarioRunnerDebugMode extends ScenarioRunnerProto {

    private Scanner sc;

    public ScenarioRunnerDebugMode(Config cfg) {
        super(cfg);
        this.sc = new Scanner(System.in);
    }

    @Override
    protected void end() {
        this.sc.close();
        this.sc = null;
    }

    @Override
    public WebDriver execute_sentence(String sentence, WebDriver driver, Config cfg) {

        super.printScriptSentences(sentence);

        if (cfg.getOption().isDebugMode()) {
            String enter = "(Enter) : execute command";
            String end = "(end) : end debug mode";
            String quit = "(quit) : force quit program";
            String msg = String.format("next command : %s\n    %s\n    %s\n    %s\n", sentence, enter, end, quit);

            System.out.println(msg);

            String input = "";
            if (this.sc.hasNextLine()) {
                input = this.sc.nextLine().trim();
            }

            switch (input) {
            case "end":
                this.debug_end(cfg);
                break;
            case "quit":
                this.debug_quit(cfg);
            default:
                break;
            }
        }

        String function = super.getFunction(sentence);
        String param = super.getParam(sentence);

        Command command = super.register.getCommand(function);

        command.prepare(driver, cfg, param, this, sentence);

        if (command.is_enable()) {
            driver = command.execute();
            command.is_success();
        }

        return driver;
    }

    private void debug_end(Config cfg) {
        cfg.getOption().disable_debugMode();
    }

    private void debug_quit(Config cfg) {
        System.exit(0);
    }

}
