package scenariorunner;

import java.io.InputStreamReader;
import java.util.Scanner;
import org.openqa.selenium.WebDriver;
import command.Command;
import config.Config;

public class ScenarioRunnerSyntaxCheck extends ScenarioRunnerProto {

    private Scanner sc;

    public ScenarioRunnerSyntaxCheck(Config cfg, String name) {
        super(cfg, name);
        this.sc = new Scanner(new InputStreamReader(System.in));
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
        command.prepare(driver, this, sentence, param);

        boolean is_valid = command.is_valid_syntax();

        if(!is_valid){
            command.execute();
            System.out.println("    invalid syntax : " + sentence);
        }

        return driver;
    }

}
