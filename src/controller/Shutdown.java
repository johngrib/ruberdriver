package controller;

import java.util.logging.Level;
import scenariorunner.ScenarioRunner;

public class Shutdown extends Thread {

    private ScenarioRunner runner;

    public Shutdown(ScenarioRunner runner) {
        this.runner = runner;
    }

    public void run() {

        String key = runner.getDriverKey();
        String msg = runner.getFinishStatusString() + " " + key;
        String closed = "CLOSED WebDriver : " + key;

        runner.getLogger().log(Level.INFO, msg);
        Main.logger.log(Level.INFO, msg);

        runner.stop();
        Main.driverManager.quitDriver(key);

        runner.getLogger().log(Level.INFO, closed);
        Main.logger.log(Level.INFO, closed);
    }
}