package config;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import org.json.simple.JSONObject;
import lombok.Getter;
import model.Item;
import util.FileHelper;
import util.ItemBuilder;
import util.JSONHelper;

public class Config {

    @Getter
    private JSONObject json;

    @Getter
    private HashMap<String, Item> items;

    @Getter
    private HashMap<String, Item> scenarios;

    @Getter
    private String picsPath;

    @Getter
    private Options option;

    public Config(Options option) {

        ItemBuilder builder = new ItemBuilder();

        this.json = new JSONHelper().getJsonObject(getSourcePath(option.getSource()));
        this.items = builder.getItemMap(json, Const.ITEM);
        this.scenarios = builder.getItemMap(json, Const.SCENARIO);
        this.picsPath = setPicsPath(json);
        this.option = option;

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