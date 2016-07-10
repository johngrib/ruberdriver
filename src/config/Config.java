package config;

import java.io.File;
import java.io.FileNotFoundException;
import org.json.simple.JSONObject;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import lombok.Getter;
import util.FileHelper;
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

    public Config(final String[] args) {

        new JCommander(this, args);

        final String location = new FileHelper().getAbsolutePath(source);

        this.json = new JSONHelper().getJsonObject(location);
        this.build_options(this.json);
    }

    private Config build_options(JSONObject json) {
        setChromeDriver(json);
        return this;
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