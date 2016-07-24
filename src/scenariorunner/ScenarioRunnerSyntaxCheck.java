package scenariorunner;

import java.util.logging.Level;

import org.openqa.selenium.WebDriver;
import command.Command;

public class ScenarioRunnerSyntaxCheck extends ScenarioRunnerProto {

    public ScenarioRunnerSyntaxCheck(String name) {
        super(name);
    }

    @Override
    public WebDriver execute_sentence(String sentence, WebDriver driver) {

        String function = super.getFunction(sentence);
        String param = super.getParam(sentence);

        Command command = super.register.getCommand(function);
        command.prepare(driver, this, sentence, param);

        boolean is_valid = command.is_valid_syntax();

        if(!is_valid){
            command.execute();
            String msg = "invalid syntax : " + sentence;
            getLogger().log(Level.SEVERE, msg);
        }

        return driver;
    }

}
