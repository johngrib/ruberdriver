package config;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import org.json.simple.JSONObject;
import controller.Main;
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
    private String logPath;

    public Config() {

        ItemBuilder builder = new ItemBuilder();

        this.json = new JSONHelper().getJsonObject(getSourcePath(Main.option.getSource()));
        this.items = builder.getItemMap(json, Const.ITEM);
        this.scenarios = builder.getItemMap(json, Const.SCENARIO);
        this.picsPath = createPath(json, "pics", "./ruberdriver_pics/");
        this.logPath = createPath(json, "log_path", "./ruberdriver_log/");

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

    private String createPath(JSONObject json, String path, String new_path) {

        if (!json.containsKey(path)) {
            File new_dir = new File(new_path);
            if (!new_dir.exists()) {
                new_dir.mkdirs();
            }
            return new_path;
        }

        final String chr_path = (String) json.get(path);
        final String abs_path = new FileHelper().getAbsolutePath(chr_path) + "/";
        File new_dir = new File(abs_path);

        if (!new_dir.exists()) {
            new_dir.mkdirs();
        }
        return abs_path;
    }
}