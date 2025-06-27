package utilities;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.time.Duration;


public class WebDriverManagerUtil {

    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    private static final int IMPLICIT_WAIT_TIMEOUT = 5;
    private static final int PAGE_LOAD_TIMEOUT = 15;

    public static void initializeDriver(boolean headless) {
        WebDriver webDriver = null;

        try {
            WebDriverManager.chromedriver().setup();
            ChromeOptions chromeOptions = getChromeOptions(headless);
            webDriver = new ChromeDriver(chromeOptions);

            webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(IMPLICIT_WAIT_TIMEOUT));
            webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(PAGE_LOAD_TIMEOUT));

            if (!headless) {
                webDriver.manage().window().maximize();
            }

            driver.set(webDriver);

        } catch (Exception e) {
            if (webDriver != null) {
                webDriver.quit();
            }
            throw new RuntimeException("Failed to initialize Chrome WebDriver", e);
        }
    }

    private static ChromeOptions getChromeOptions(boolean headless) {
        ChromeOptions chromeOptions = new ChromeOptions();
        if (headless) {
            chromeOptions.addArguments("--headless");
        }
        chromeOptions.addArguments("--no-sandbox");
        chromeOptions.addArguments("--disable-dev-shm-usage");
        chromeOptions.addArguments("--disable-gpu");
        chromeOptions.addArguments("--window-size=1920,1080");
        chromeOptions.addArguments("--disable-extensions");
        chromeOptions.addArguments("--disable-web-security");
        chromeOptions.addArguments("--allow-running-insecure-content");
        return chromeOptions;
    }

    public static WebDriver getDriver() {
        WebDriver currentDriver = driver.get();
        if (currentDriver == null) {
            throw new IllegalStateException("WebDriver is not initialized. Call initializeDriver() first.");
        }
        return currentDriver;
    }

    public static boolean isDriverInitialized() {
        return driver.get() != null;
    }

    /**
     * Quit WebDriver and remove from ThreadLocal
     */
    public static void quitDriver() {
        WebDriver currentDriver = driver.get();
        if (currentDriver != null) {
            try {
                currentDriver.quit();
            } catch (Exception e) {
                System.err.println("Error while quitting driver: " + e.getMessage());
            } finally {
                driver.remove();
            }
        }
    }

    /**
     * Refresh current page
     */
    public static void refreshPage() {
        getDriver().navigate().refresh();
    }

    /**
     * Execute JavaScript
     */
    public static Object executeScript(String script, Object... args) {
        return ((org.openqa.selenium.JavascriptExecutor) getDriver()).executeScript(script, args);
    }

    /**
     * Wait for page to load completely
     */
    public static void waitForPageLoad() {
        org.openqa.selenium.support.ui.WebDriverWait wait =
                new org.openqa.selenium.support.ui.WebDriverWait(getDriver(), Duration.ofSeconds(30));

        wait.until(driver ->
                executeScript("return document.readyState").equals("complete"));
    }
}