package config;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;

import org.json.simple.JSONObject;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import lombok.Getter;
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
    private JSONObject json;

    @Getter
    private HashMap<String, Item> items;

    public Config(final String[] args) {

        new JCommander(this, args);

        this.json = new JSONHelper().getJsonObject(getSourcePath(source));
        this.items = new ItemBuilder().getItemMap(json);

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
}