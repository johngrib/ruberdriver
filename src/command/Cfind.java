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

            WebElement element = getLastElement().findElement(by);
            setLastElement(element);

        } catch (org.openqa.selenium.NoSuchElementException e){
            System.out.println("---Cfind ERROR---");
            System.out.println(String.format("Invalid query : [%s]", this.sentence));
            String msg = e.getMessage().replaceAll("^", "    ");
            System.out.println(msg);
            System.out.println("----------------");
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
        }

        return this.driver;
    }

}
