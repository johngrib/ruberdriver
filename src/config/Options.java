package config;

import org.junit.Assert;
import org.junit.Test;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import lombok.Getter;

public class Options {

    public Options build(final String[] args) {
        new JCommander(this, args);
        this.scenarioArray = parseScenarioStringToArray(this.getRunScenario());
        return this;
    }

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

    @Parameter(names = { "--print", "-p" })
    @Getter
    private boolean printScriptSentences = false;

    @Parameter(names = { "--all", "-l" })
    @Getter
    private boolean allScenario = false;

    @Getter
    private String[] scenarioArray = { "" };

    public void disable_debugMode() {
        this.debugMode = false;
    }

    public void disable_printScriptSentences() {
        this.printScriptSentences = false;
    }

    private String[] parseScenarioStringToArray(String runScenario) {

        String[] scenarios = runScenario.trim().split("\\s*,\\s*");

        return scenarios;
    }

    @Test
    public void testParse() {
        String[] rs = parseScenarioStringToArray(" test, test2:3, test 4 , test 12 ,");
        String[] expect = { "test", "test2:3", "test 4", "test 12" };

        Assert.assertArrayEquals(expect, rs);
    }
}
