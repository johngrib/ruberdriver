package config;

import java.util.HashMap;

import org.json.simple.JSONObject;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

import lombok.Getter;

public class Config {

    @Getter
    @Parameter(names = { "--source", "-s" })
    private String source = "ruberdriver.json";

    @Getter
    @Parameter(names = { "--async", "-a" })
    private boolean async = false;
    
    public Config build(String[] args) {
        Config cfg = new Config();
        new JCommander(cfg, args);
        return cfg;
    }

    private JSONObject getJsonObject(String source){

        return null;
    }
}