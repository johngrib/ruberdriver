package command;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
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
        LocalDate today = LocalDate.now();
        LocalTime time = LocalTime.now();
        String file_name = String.format("%s_%s_%s.png", today.toString(), time.toString(), runner.getName().trim());

        try {
            FileUtils.copyFile(scrFile, new File(Main.cfg.getPicsPath() + file_name));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
