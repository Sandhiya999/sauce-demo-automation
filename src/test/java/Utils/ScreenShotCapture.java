package Utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;

public class ScreenShotCapture {
    public static String captureScreeshot(WebDriver driverInstance,String testName)
    {
        String screeshotName = testName+ "_" + System.currentTimeMillis() + ".png";
        String relativepath = "screenshots/" + screeshotName;
        String abspath = System.getProperty("user.dir")+"/reports/" +relativepath;
        File src = ((TakesScreenshot) driverInstance).getScreenshotAs(OutputType.FILE);
        File dest = new File(abspath);
       dest.getParentFile().mkdirs();
        try
        {
            FileUtils.copyFile(src,dest);
        } catch (IOException e) {
            throw new RuntimeException("Failed to capture screenshot ", e);
        }
        return relativepath;

    }
}
