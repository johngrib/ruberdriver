package config;

import org.json.simple.JSONObject;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import lombok.Getter;
import lombok.Setter;
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
    @Setter
    private JSONObject json;

    public Config build(String[] args) {

        Config cfg = new Config();
        new JCommander(cfg, args);

        final String location = new FileHelper().getAbsolutePath(source);
        final JSONObject json = new JSONHelper().getJsonObject(location);

        cfg.setJson(json);

        return cfg;
    }

}