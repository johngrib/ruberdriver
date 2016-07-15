package config;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;

import org.json.simple.JSONObject;
import org.openqa.selenium.WebElement;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

import controller.CommandRegister;
import lombok.Getter;
import lombok.Setter;
import model.Item;
import util.FileHelper;
import util.ItemBuilder;
import util.JSONHelper;

public class Config {

    @Getter
    @Parameter(names = { "--source", "-s" })
    private String source = "ruberdriver.json";

    @Getter
    @Parameter(names = { "--async", "-a" })
    private boolean async = false;

    @Getter
    @Parameter(names = { "--scenario", "-c" })
    private String runScenario = "";

    @Parameter(names = { "--debug", "-d" })
    @Getter
    private boolean debugMode = false;

    @Getter
    private JSONObject json;

    @Getter
    private HashMap<String, Item> items;

    @Getter
    private HashMap<String, Item> scenarios;

    @Getter
    private CommandRegister register;

    @Getter
    @Setter
    private WebElement lastElement;

    @Getter
    private String picsPath;

    public Config(final String[] args) {

        new JCommander(this, args);

        ItemBuilder builder = new ItemBuilder();

        this.register = new CommandRegister();
        this.json = new JSONHelper().getJsonObject(getSourcePath(source));
        this.items = builder.getItemMap(json, Const.ITEM);
        this.scenarios = builder.getItemMap(json, Const.SCENARIO);
        this.picsPath = setPicsPath(json);

        setChromeDriver(json);
    }

    private String getSourcePath(String source) {
        return new FileHelper().getAbsolutePath(source);
    }

    private void setChromeDriver(JSONObject json) {

        final String chrome_drv = "chromedriver";
        final String driver_key = "webdriver.chrome.driver";
        final String error_text = "can't found chromedriver\n";

        if (!json.containsKey(chrome_drv)) {
            return;
        }

        final String chr_path = (String) json.get(chrome_drv);
        final String abs_path = new FileHelper().getAbsolutePath(chr_path);

        if (new File(abs_path).exists()) {
            System.setProperty(driver_key, abs_path);
        } else {
            new FileNotFoundException(error_text);
        }
    }

    private String setPicsPath(JSONObject json) { 

        final String pics = "pics";
        final String error_text = "can't found pics path \n";

        if (!json.containsKey(pics)) {
            return System.getProperty("user.home");
        }

        final String chr_path = (String) json.get(pics);
        final String abs_path = new FileHelper().getAbsolutePath(chr_path);

        if (new File(abs_path).exists()) {
            return abs_path;
        } else {
            new FileNotFoundException(error_text);
            return System.getProperty("user.home");
        }
    }
}