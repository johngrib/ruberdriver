package controller;

import scenariorunner.ScenarioRunner;

public class Shutdown extends Thread {

    private ScenarioRunner runner;

    public Shutdown(ScenarioRunner runner) {
        this.runner = runner;
    }

    public void run() {
        System.out.println("\n^C Input : close webdriver - " + runner.getDriverKey());
        runner.stop();
        String key = runner.getDriverKey();
        Main.driverManager.quitDriver(key);
    }
}