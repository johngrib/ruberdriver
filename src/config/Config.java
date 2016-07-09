package config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Assert;
import org.junit.Test;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import lombok.Getter;
import lombok.Setter;

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
        cfg.setJson(getJsonObject(getAbsoluteDir(source)));

        return cfg;
    }

    private JSONObject getJsonObject(final String sourceLoc) {

        JSONObject jobj = null;
        try {
            Object obj = new JSONParser().parse(new FileReader(sourceLoc));
            jobj = (JSONObject) obj;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return jobj;
    }

    private String getAbsoluteDir(final String source) {

        String location;

        if (source.startsWith("~/")) {
            location = applyHomeDir(source);
        } else if (source.startsWith("./")) {
            location = applyWorkDir(source);
        } else {
            location = source;
        }

        if (!new File(location).exists()) {
            new FileNotFoundException("can't found file : " + source);
        }

        return location;
    }

    @Test
    public void testGetSourceDir() {
        String actual = getAbsoluteDir("./pom.xml");
        String expect = System.getProperty("user.dir") + "/pom.xml";
        Assert.assertEquals("getSourceDir", actual, expect);

        String actual1 = getAbsoluteDir("~/.bash_profile");
        String expect1 = System.getProperty("user.home") + "/.bash_profile";
        Assert.assertEquals("getSourceDir1", actual1, expect1);
    }

    private String applyHomeDir(String dir) {
        return dir.replaceFirst("^\\~\\/", System.getProperty("user.home") + "/");
    }

    @Test
    public void testApplyHomeDir() {
        String expect = System.getProperty("user.home") + "/";
        String actual = applyHomeDir("~/");
        Assert.assertEquals("applyHomeDir", actual, expect);
    }

    private String applyWorkDir(String dir) {
        return dir.replaceFirst("^\\.\\/", System.getProperty("user.dir") + "/");
    }

    @Test
    public void testApplyWorkDir() {
        String expect = System.getProperty("user.dir") + "/";
        String actual = applyWorkDir("./");
        Assert.assertEquals("applyWorkDir", expect, actual);
    }
}