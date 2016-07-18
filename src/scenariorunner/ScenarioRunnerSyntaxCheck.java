package scenariorunner;

import java.util.Scanner;

import org.openqa.selenium.WebDriver;
import command.Command;
import config.Config;

public class ScenarioRunnerSyntaxCheck extends ScenarioRunnerProto {

    private Scanner sc;

    public ScenarioRunnerSyntaxCheck(Config cfg) {
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

        String function = super.getFunction(sentence);
        String param = super.getParam(sentence);

        Command command = super.register.getCommand(function);
        command.prepare(driver, cfg, param, this, sentence);

        boolean is_valid = command.is_valid_syntax();

        if(!is_valid){
            System.out.println("    invalid syntax : " + sentence);
        }

        return driver;
    }

}
