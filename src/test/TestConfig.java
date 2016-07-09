package test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import config.Config;

public class TestConfig {

    private Config cfg;
    private String source = "./ruberdriver.json";

    @Before
    public void setUp() {
        String[] args = { "--source", source };
        cfg = new Config(args);
    }

    @Test
    public void config_source_option() {
        String msg = "Test --source option";
        Assert.assertEquals(msg, cfg.getSource(), source);
    }
}
