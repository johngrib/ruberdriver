package controller;

import java.util.Iterator;
import java.util.LinkedList;
import org.junit.Test;
import config.Config;
import config.Options;

public class Main {

    public static void main(String[] args) {

        Main main = new Main();
        Options option = new Options(args);
        Config cfg = new Config(option);

        if (option.isAsync()) {
            main.runAsync(cfg, option);
        } else {
            new Ruberdriver(cfg).run();
            System.exit(0);
        }
    }

    public void runAsync(Config cfg, Options option) {

        if (option.isAllScenario()) {

            LinkedList<Thread> threads = new LinkedList<>();

            Iterator<String> it = cfg.getScenarios().keySet().iterator();

            while (it.hasNext()) {
                String scenario = (String) it.next();
                Thread rd = new Ruberdriver(cfg, scenario);
                threads.add(rd);
                rd.start();
            }

            Iterator<Thread> tit = threads.iterator();
            try {
                while (tit.hasNext()) {
                    Thread thread = (Thread) tit.next();
                    thread.join();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return;
    }

    @Test
    public void mainTest() {
        // String[] args = { "--source", "test.json", "--scenario", "purchase_test" };
        String[] args = { "--source", "test.json", "--scenario", "purchase_test", "-a", "-l" };
        // String[] args = { "--source", "test.json", "--scenario", "purchase_test", "-d" };
        // String[] args = { "--source", "test.json", "--scenario", "login_test" };
        // String[] args = { "--scenario", "go_test" };
        Main.main(args);
    }
}
