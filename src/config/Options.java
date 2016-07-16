package config;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import lombok.Getter;

public class Options {

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

    public Options(final String[] args) {

        new JCommander(this, args);

    }

    public void disable_debugMode() {
        this.debugMode = false;
    }

    public void disable_printScriptSentences() {
        this.printScriptSentences = false;
    }

}
