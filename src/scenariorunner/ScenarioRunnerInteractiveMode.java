package scenariorunner;

import java.io.InputStreamReader;
import java.util.Scanner;

import org.openqa.selenium.WebDriver;
import command.Command;
import config.Config;

public class ScenarioRunnerInteractiveMode extends ScenarioRunnerProto {

    private Scanner sc;

    public ScenarioRunnerInteractiveMode(Config cfg, String name) {
        super(cfg, name);
        this.sc = new Scanner(new InputStreamReader(System.in));
    }

    @Override
    protected void end() {
    }

    @Override
    public WebDriver execute_sentence(String sentence, WebDriver driver, Config cfg) {

        super.printScriptSentences(sentence);

        if (cfg.getOption().isInteractiveMode()) {
            String enter = "(Enter) : execute command";
            String end = "(end) : end interactive mode";
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

        command.prepare(driver, this, sentence, param);

        if (command.is_enable()) {
            driver = command.execute();
            command.is_success();
        }

        return driver;
    }

    private void debug_end(Config cfg) {
        cfg.getOption().disable_interactiveMode();
    }

    private void debug_quit(Config cfg) {
        System.exit(0);
    }

}
