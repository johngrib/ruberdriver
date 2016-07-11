package command;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class Printscreen extends CommandProto {

    @Override
    public WebDriver execute() {
        getscreenshot();
        return this.driver;
    }

    public void getscreenshot() {
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

        LocalDate today = LocalDate.now();

        int year = today.getYear();
        int month = today.getMonthValue();
        int day = today.getDayOfMonth();

        LocalTime time = LocalTime.now();
        int hour = time.getHour();
        int min = time.getMinute();
        int sec = time.getSecond();
        int nano = time.getNano();

        String file_name = year + "." + month + "." + day + "_" + hour + "h." + min + "m." + sec + "s." + nano + "n"
                + ".png";

        try {
            FileUtils.copyFile(scrFile, new File(this.cfg.getPicsPath() + file_name));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
