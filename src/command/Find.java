package command;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import org.openqa.selenium.By;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import checker.ParameterDefinedMethod;
import checker.ParameterNotNull;
import checker.ParameterSecondNotNull;
import controller.Main;

public class Find extends CommandProto implements ParameterNotNull, ParameterDefinedMethod, ParameterSecondNotNull {

    private Set<String> param_methods;

    @Override
    public WebDriver execute() {

        String function = getFunction(this.param);
        String query = getParam(this.param);

        try {

            Method method = By.class.getMethod(function, String.class);
            By by = (By) method.invoke(By.class, query);

            WebElement element = this.driver.findElement(by);
            setLastElement(element);
            return driver;

        } catch (org.openqa.selenium.NoSuchElementException e) {
            System.out.println("---Find ERROR---");
            System.out.println(String.format("Invalid query : [%s]", this.sentence));
            String msg = e.getMessage().replaceAll("^", "    ");
            System.out.println(msg);
            System.out.println("----------------");
            runner.getLogger().log(Level.SEVERE, msg);
        } catch (UnhandledAlertException e) {
            System.out.println("---Alert ERROR---");
            String msg = e.getMessage().replaceAll("^", "    ");
            System.out.println(msg);
            System.out.println("----------------");
            runner.getLogger().log(Level.SEVERE, msg);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException | SecurityException e) {
            if (!Main.option.isSyntaxCheck()) {
                super.is_valid_syntax();
            }
        }

        runner.stop();
        return this.driver;
    }

    @Override
    public Set<String> get_defined_param_methods() {
        if (this.param_methods == null) {
            this.param_methods = new TreeSet<>();

            Method[] ms = By.class.getDeclaredMethods();

            for (Method method : ms) {
                Class<?>[] pType = method.getParameterTypes();

                if (pType.length == 1 && "java.lang.String".equals(pType[0].getName())) {
                    this.param_methods.add(method.getName());
                }
            }
        }
        return this.param_methods;
    }
}
