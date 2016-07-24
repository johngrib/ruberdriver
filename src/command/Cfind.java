package command;

import org.openqa.selenium.WebDriver;

public class Cfind extends Find {

    @Override
    public WebDriver execute() {
        return super.execute("Cfind");
    }

}
