package utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ScreenshotUtil {
    public static void captureScreenshot(WebDriver driver, String fileName) {
        try {
            // Define the path where the screenshot will be saved
            Path screenshotPath = Paths.get("reports/screenshots/" + fileName + ".png");

            // ✅ Create parent directories if they don't exist
            Files.createDirectories(screenshotPath.getParent());

            // Take screenshot and save
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            Files.copy(screenshot.toPath(), screenshotPath);

            System.out.println("Screenshot saved at: " + screenshotPath);
        } catch (Exception e) {
            System.out.println("Screenshot failed: " + e.getMessage());
        }
    }
}
