package controller;

import org.junit.Test;

import config.Config;

public class Main {

    public static void main(String[] args) {

        Config cfg = new Config(args);
        Ruberdriver rd = new Ruberdriver(cfg);
        rd.start();
        try {
            rd.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void mainTest() {
        String[] args = { "--source", "test.json", "--scenario", "purchase_test", "-d" };
        //String[] args = { "--source", "test.json", "--scenario", "login_test" };
        //String[] args = { "--scenario", "go_test" };
        Main.main(args);
    }
}
