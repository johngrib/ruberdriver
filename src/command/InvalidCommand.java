package command;

import org.openqa.selenium.WebDriver;

public class InvalidCommand extends CommandProto {

    @Override
    public WebDriver execute() {

        String msg = String.format("[%s] is invalid command.", this.getFunction(sentence));
        System.out.println(msg);

        return this.driver;
    }

}
