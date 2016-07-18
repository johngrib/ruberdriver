package command;

import java.lang.reflect.Method;
import java.util.Set;
import java.util.TreeSet;
import org.openqa.selenium.WebDriver;
import checker.ParameterDefinedMethod;
import checker.ParameterNotNull;

public class Alert extends CommandProto implements ParameterNotNull, ParameterDefinedMethod {

    private Set<String> param_methods;

    @Override
    public boolean is_enable() {
        return true;
    }

    @Override
    public WebDriver execute() {

        org.openqa.selenium.Alert alert = this.driver.switchTo().alert();

        if ("accept".equals(this.param)) {
            alert.accept();
        } else {
            alert.dismiss();
        }

        this.driver.switchTo().defaultContent();

        return this.driver;
    }

    @Override
    public Set<String> get_defined_param_methods() {
        if (this.param_methods == null) {
            this.param_methods = new TreeSet<>();

            Method[] ms = org.openqa.selenium.Alert.class.getDeclaredMethods();

            for (Method method : ms) {
                Class<?>[] pType = method.getParameterTypes();
                Class<?> rType = method.getReturnType();

                if (pType.length == 0 && "void".equals(rType.getName())) {
                    this.param_methods.add(method.getName());
                }
            }
        }
        return this.param_methods;
    }

}
