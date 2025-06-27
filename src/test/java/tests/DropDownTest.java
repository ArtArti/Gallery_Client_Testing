package tests;

import base.BaseTest;
import data.LoginData;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.AuthFormPage;
import pages.DropDownPage;
import pages.HomePage;
import utilities.WebDriverManagerUtil;
import java.time.Duration;

public class DropDownTest extends BaseTest {
    private HomePage homePage;
    private AuthFormPage authFormPage;
    private DropDownPage dropDownPage;
    private WebDriverWait wait;

    @Override
    @BeforeClass(groups = {"dropdown"}, dependsOnMethods = {"setUpClass"})
    public void initializePageObjects() {
        Reporter.log("Initializing page objects for DropDownTest", true);
        homePage = new HomePage();
        authFormPage = new AuthFormPage();
        dropDownPage = new DropDownPage();
        wait = new WebDriverWait(WebDriverManagerUtil.getDriver(), Duration.ofSeconds(10));
    }

    @Test(priority = 1, groups = {"dropdown"})
    public void testLoginWithValidCredentials() {
        homePage.clickProfileButton();
        authFormPage.performLogin(LoginData.VALID_EMAIL, LoginData.VALID_PASSWORD);
        Assert.assertTrue(authFormPage.isLoginSuccessful(), "Login should be successful.");
    }

    @Test(priority = 2, groups = {"dropdown"}, dependsOnMethods = {"testLoginWithValidCredentials"})
    public void testDropdownMenuIsVisible() {
        Assert.assertTrue(dropDownPage.isAvatarVisible(), "Avatar should be visible after login.");
        dropDownPage.clickAvatar();
        Assert.assertTrue(dropDownPage.isDropdownVisible(), "Dropdown menu should be visible.");
    }

    @Test(priority = 3, groups = {"dropdown"}, dependsOnMethods = {"testDropdownMenuIsVisible"})
    public void testClickContactLink() {
        // Store current URL to ensure navigation happens
        String currentUrl = WebDriverManagerUtil.getDriver().getCurrentUrl();

        // Dropdown should already be open from previous test
        dropDownPage.clickContact();

        // Wait for URL to change and contain "/contact"
        wait.until(ExpectedConditions.urlContains("/contact"));

        // Debug logging
        String newUrl = WebDriverManagerUtil.getDriver().getCurrentUrl();
        Reporter.log("Previous URL: " + currentUrl, true);
        Reporter.log("New URL after clicking Contact: " + newUrl, true);

        Assert.assertTrue(newUrl.contains("/contact"),
                "Should navigate to Contact page. Current URL: " + newUrl);
    }

    @Test(priority = 4, groups = {"dropdown"}, dependsOnMethods = {"testLoginWithValidCredentials"})
    public void testClickChangePassword() {
        // Store current URL to ensure navigation happens
        String currentUrl = WebDriverManagerUtil.getDriver().getCurrentUrl();

        // This test is independent - open fresh dropdown
        dropDownPage.clickAvatar();
        Assert.assertTrue(dropDownPage.isDropdownVisible(), "Dropdown should be open before clicking change password.");

        dropDownPage.clickChangePassword();

        wait.until(ExpectedConditions.urlContains("/changepassword"));

        // Debug logging
        String newUrl = WebDriverManagerUtil.getDriver().getCurrentUrl();
        Reporter.log("Previous URL: " + currentUrl, true);
        Reporter.log("New URL after clicking Change Password: " + newUrl, true);

        Assert.assertTrue(newUrl.contains("/changepassword"),
                "Should navigate to Change password page. Current URL: " + newUrl);
    }

    @Test(priority = 5, groups = {"dropdown"}, dependsOnMethods = {"testLoginWithValidCredentials"})
    public void testHoverOverDropDownItems() {
        // This test is independent - open fresh dropdown
        dropDownPage.clickAvatar();

        // Step 2: Assert dropdown is visible
        Assert.assertTrue(dropDownPage.isDropdownVisible(), "Dropdown should be visible after avatar click.");

        // Step 3: Hover over each menu item
        dropDownPage.hoverOverMenuItem("Change Password");
        dropDownPage.hoverOverMenuItem("Contact");
        dropDownPage.hoverOverMenuItem("Log Out");
        dropDownPage.clickLogout();

        // Wait for logout to complete
//        wait.until(ExpectedConditions.visibilityOf());

        Assert.assertTrue(homePage.isProfileButtonVisible(), "Should be redirected to login after logout.");
    }
}