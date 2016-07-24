package command;

import java.util.logging.Level;
import org.openqa.selenium.WebDriver;

public class InvalidCommand extends CommandProto {

    @Override
    public WebDriver execute() {

        String msg = String.format("[%s] is invalid command.", this.getFunction(sentence));
        runner.getLogger().log(Level.SEVERE, msg);

        return this.driver;
    }

    @Override
    public boolean is_valid_syntax() {
        return false;
    }

}
