package controller;

import config.Config;

public class Ruberdriver {

    public Ruberdriver(Config cfg) {
    }

    public static void main(String[] args) {

        Config cfg = new Config().build(args);
        Ruberdriver rd = new Ruberdriver(cfg);

    }
}
