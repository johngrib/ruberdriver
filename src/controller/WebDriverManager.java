package controller;

import java.util.Collection;
import java.util.HashMap;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.openqa.selenium.safari.SafariDriver;

/**
 * 여러 scenario runner 가 사용하는 WebDriver 를 관리한다.
 * @author johngrib
 */
public class WebDriverManager {

    private HashMap<String, WebDriver> drivers = new HashMap<>();

    public WebDriver getDriver(String key) {
        return drivers.get(key);
    }

    private void putDriver(String key, WebDriver driver) {
        drivers.put(key, driver);
    }

    public WebDriver getNewChromeDriver(String key) {
        WebDriver driver = new ChromeDriver();
        putDriver(key, driver);
        return driver;
    }

    public WebDriver getNewSafariDriver(String key) {
        WebDriver driver = new SafariDriver();
        putDriver(key, driver);
        return driver;
    }

    public WebDriver getNewFireFoxDriver(String key) {
        WebDriver driver = new FirefoxDriver();
        putDriver(key, driver);
        return driver;
    }

    public Collection<String> getDriverList() {
        return drivers.keySet();
    }

    public Collection<WebDriver> getDrivers() {
        return drivers.values();
    }

    public void closeDriver(String key) {
        WebDriver driver = drivers.get(key);
        if (driver != null) {
            driver.close();
        }
        putDriver(key, null);
    }

    public void quitDriver(String key) {
        WebDriver driver = drivers.get(key);
        if (driver != null && !driver.toString().matches("\\(null\\)\\s*$")) {
            try {
                driver.quit();
            } catch (UnreachableBrowserException e) {
                if (Main.option.isDebugMode())
                    e.printStackTrace();
            }
        }
        putDriver(key, null);
    }

    public void claosAllDrivers() {
        Collection<WebDriver> drv = drivers.values();
        for (WebDriver driver : drv) {
            if (driver != null && !driver.toString().matches("\\(null\\)\\s*$")) {
                try {
                    driver.close();
                } catch (UnreachableBrowserException e) {
                    if (Main.option.isDebugMode())
                        e.printStackTrace();
                }
            }
        }
    }
}
