package test;

import org.junit.Test;

import controller.Main;

public class MainTest {

    @Test
    public void mainTest() {
        String[] args = { //
                "--source", "test.json", //
                "-l", "-a"
                //"--scenario", "purchase_test", "-a", "-l" //
                // "-c", "purchase_test", "-d" //
                // "-c", "purchase_test" //
                // "-c", "login_test:2,purchase_test:2" //
        };
        Main.main(args);
    }
}
