package command;

import java.util.Set;
import java.util.TreeSet;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import checker.ParameterDefinedMethod;
import controller.Main;

public class Open extends CommandProto implements ParameterDefinedMethod {

    private Set<String> param_methods;

    @Override
    public boolean is_enable() {
        return true;
    }

    @Override
    public WebDriver execute() {

        String key = this.runner.getDriverKey();
        switch (this.param) {
        case "chrome":
            this.driver = Main.driverManager.getNewChromeDriver(key);
            break;
        case "safari":
            this.driver = Main.driverManager.getNewSafariDriver(key);
            break;
        case "firefox":
            this.driver = Main.driverManager.getNewFireFoxDriver(key);
            break;
        default:
            this.driver = Main.driverManager.getNewChromeDriver(key);
            break;
        }

        this.wait = new WebDriverWait(this.driver, 30);
        return this.driver;
    }

    @Override
    public boolean is_success() {
        return this.driver != null;
    }

    @Override
    public Set<String> get_defined_param_methods() {
        if (this.param_methods == null) {
            this.param_methods = new TreeSet<>();
            this.param_methods.add("chrome");
            this.param_methods.add("safari");
            this.param_methods.add("firefox");
        }
        return this.param_methods;
    }

}
