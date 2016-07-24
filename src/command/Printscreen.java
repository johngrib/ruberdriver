package command;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import controller.Main;

public class Printscreen extends CommandProto {

    @Override
    public WebDriver execute() {
        getscreenshot();
        return this.driver;
    }

    public void getscreenshot() {

        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String file_name = String.format("%s--%s.png", runner.getDriverKey(), runner.getProgressString().replaceAll("\\/", ":"));

        try {
            FileUtils.copyFile(scrFile, new File(Main.cfg.getPicsPath() + file_name));
        } catch (IOException e) {
            if (Main.option.isDebugMode())
                e.printStackTrace();
        }
    }
}
