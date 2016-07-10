package command;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Cfind extends Find {

    @Override
    public WebDriver execute() {

        String function = getFunction(this.param);
        String query = getParam(this.param);

        try {

            Method method = By.class.getMethod(function, String.class);
            By by = (By) method.invoke(By.class, query);
            
            WebElement element = this.cfg.getLastElement().findElement(by);

            cfg.setLastElement(element);

        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
        }

        return this.driver;
    }

}