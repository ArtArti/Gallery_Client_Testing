package base;

import org.testng.Reporter;
import org.testng.annotations.*;
import utilities.ConfigReader;
import utilities.WebDriverManagerUtil;

public class BaseTest {

    // ==================== CLASS-LEVEL SETUP (For DropDownTest) ====================

    @BeforeClass(groups = {"dropdown", "dependent"})
    @Parameters({"headless"})
    public void setUpClass(@Optional("false") String headless) {
        Reporter.log("Setting up driver (headless: " + headless + ") - Class Level", true);

        WebDriverManagerUtil.initializeDriver(Boolean.parseBoolean(headless));
        WebDriverManagerUtil.getDriver().get(ConfigReader.get("baseUrl"));
    }

    @BeforeClass(groups = {"dropdown", "dependent"}, dependsOnMethods = {"setUpClass"})
    public void initializePageObjects() {
        Reporter.log("Initializing page objects - Class Level", true);
        // Override this in child classes if needed
    }

    @AfterClass(groups = {"dropdown", "dependent"})
    public void tearDownClass() {
        if (WebDriverManagerUtil.isDriverInitialized()) {
            Reporter.log("Tearing down driver: " +
                    WebDriverManagerUtil.getDriver().getClass().getSimpleName() + " - Class Level", true);

            WebDriverManagerUtil.quitDriver();
            Reporter.log("Driver cleanup completed - Class Level", true);
        }
    }

    // ==================== METHOD-LEVEL SETUP (For LoginTest only) ====================

    @BeforeMethod(groups = {"login", "independent"})
    @Parameters({"headless"})
    public void setUpMethod(@Optional("false") String headless) {
        Reporter.log("Setting up driver (headless: " + headless + ") - Method Level", true);

        WebDriverManagerUtil.initializeDriver(Boolean.parseBoolean(headless));
        WebDriverManagerUtil.getDriver().get(ConfigReader.get("baseUrl"));
    }

    @BeforeMethod(groups = {"login", "independent"}, dependsOnMethods = {"setUpMethod"})
    public void initializePageObjectsForMethod() {
        Reporter.log("Initializing page objects - Method Level", true);
        // Override this in child classes if needed
    }

    @AfterMethod(groups = {"login", "independent"})
    public void tearDownMethod() {
        if (WebDriverManagerUtil.isDriverInitialized()) {
            Reporter.log("Tearing down driver: " +
                    WebDriverManagerUtil.getDriver().getClass().getSimpleName() + " - Method Level", true);

            WebDriverManagerUtil.quitDriver();
            Reporter.log("Driver cleanup completed - Method Level", true);
        }
    }

    // ==================== SUITE-LEVEL SETUP ====================

    @BeforeSuite
    public void beforeSuite() {
        Reporter.log("Starting Test Suite execution", true);
    }

    @AfterSuite
    public void afterSuite() {
        Reporter.log("Test Suite execution completed", true);
    }

    // ==================== UTILITY METHODS ====================

    protected void navigateToHomePage() {
        Reporter.log("Navigating back to home page", true);
        WebDriverManagerUtil.getDriver().get(ConfigReader.get("baseUrl"));
    }

    protected void refreshPage() {
        Reporter.log("Refreshing current page", true);
        WebDriverManagerUtil.refreshPage();
    }
}