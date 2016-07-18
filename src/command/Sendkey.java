package command;

import java.util.Set;
import java.util.TreeSet;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

import checker.ParameterDefinedMethod;

public class Sendkey extends CommandProto implements ParameterDefinedMethod {

    private Set<String> param_methods;

    @Override
    public boolean is_enable() {
        return getLastElement().isEnabled();
    }

    @Override
    public WebDriver execute() {
        getLastElement().sendKeys(Keys.valueOf(this.param));
        return this.driver;
    }

    @Override
    public Set<String> get_defined_param_methods() {

        if (this.param_methods == null) {
            this.param_methods = new TreeSet<>();
            Keys[] list = Keys.class.getEnumConstants();

            for (Keys key : list) {
                this.param_methods.add(key.name());
            }
        }
        return this.param_methods;
    }

}
