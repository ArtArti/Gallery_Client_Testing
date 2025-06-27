// LoginTest.java - Updated for your React AuthForm component
package tests;

import base.BaseTest;
import data.LoginData;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.AuthFormPage;

import static org.testng.Assert.assertTrue;

public class LoginTest extends BaseTest {

    private HomePage homePage;
    private AuthFormPage authFormPage;

    @Override
    @BeforeMethod(groups = {"login"}, dependsOnMethods = {"setUpMethod"})
    public void initializePageObjectsForMethod() {
        Reporter.log("Initializing page objects for LoginTest", true);

        homePage = new HomePage();
        authFormPage = new AuthFormPage();
    }


    @Test(priority = 1, groups = {"login"}, description = "Verify AuthForm page loads with correct initial state")
    public void testAuthFormInitialState() {
        authFormPage = homePage.clickProfileButton();
        // Verify the form card is visible
        assertTrue(authFormPage.isFormCardVisible(),
                "Auth form card should be visible");

        // Verify switch button is visible
        assertTrue(authFormPage.isSwitchButtonVisible(),
                "Switch mode button should be visible");

        // Verify initial mode is Login (isLogin = true in your React component)
        assertTrue(authFormPage.isInLoginMode(),
                "Form should be in login mode by default");

        // Verify correct heading is displayed
        assertTrue(authFormPage.isLoginHeadingDisplayed(),
                "Login heading should be displayed initially");

        // Verify switch button shows correct text
        assertTrue(authFormPage.getSwitchButtonText().contains("Switch to Register"),
                "Switch button should show 'Switch to Register' in login mode");
    }

    @Test(priority = 2, groups = {"login"}, description = "Verify login functionality with valid credentials")
    public void testValidLogin() {
        authFormPage = homePage.clickProfileButton();

        // Perform login
        authFormPage.performLogin(LoginData.VALID_EMAIL, LoginData.VALID_PASSWORD);

        // Verify successful login
        Assert.assertTrue(authFormPage.isLoginSuccessful(),"Login should be successful with valid credentials");

    }


    @Test(priority = 3, groups = {"login"}, description = "Verify login functionality with invalid credentials")
    public void testInvalidLogin() {
        authFormPage = homePage.clickProfileButton();

        // Attempt login with invalid credentials
        authFormPage.performLogin(LoginData.INVALID_EMAIL, LoginData.INVALID_PASSWORD);

        // Verify error is displayed
        assertTrue(authFormPage.isErrorDisplayed(),
                "Error message should be displayed for invalid credentials");
    }

    @Test(priority = 4, groups = {"login"}, description = "Verify mode switching functionality")
    public void testModeSwitching() {
        authFormPage = homePage.clickProfileButton();

        // Initial state - should be login mode
        assertTrue(authFormPage.isInLoginMode(),
                "Should start in login mode");
        Assert.assertEquals(authFormPage.getFormHeading(), "Login to Your Account",
                "Should show login heading initially");

        // Switch to register mode
        authFormPage.switchToRegisterMode()
                .waitForModeTransition(); // Wait for framer-motion animation

        assertTrue(authFormPage.isInRegisterMode(),
                "Should be in register mode after switching");
        Assert.assertEquals(authFormPage.getFormHeading(), "Create a New Account",
                "Should show register heading after switching");
        assertTrue(authFormPage.getSwitchButtonText().contains("Switch to Login"),
                "Switch button should show 'Switch to Login' in register mode");

        authFormPage.switchToLoginMode()
                .waitForModeTransition(); // Wait for framer-motion animation

        assertTrue(authFormPage.isInLoginMode(),
                "Should be back in login mode");
        Assert.assertEquals(authFormPage.getFormHeading(), "Login to Your Account",
                "Should show login heading again");
    }

    @Test(priority = 5, groups = {"login"},description = "Verify registration functionality")
    public void testRegistration() {
        authFormPage = homePage.clickProfileButton();

        // Switch to register mode and perform registration
        authFormPage.switchToRegisterMode()
                .waitForModeTransition()
                .performRegistration(
                        LoginData.Registration.FULL_NAME,
                        LoginData.Registration.EMAIL,
                        LoginData.Registration.PASSWORD,
                        LoginData.Registration.CONFIRM_PASSWORD
                );

        // Verify successful registration
        assertTrue(authFormPage.isLoginSuccessful(),
                "Registration should be successful with valid data");
    }

    @Test(priority = 6, groups = {"login"}, description = "Verify form field visibility based on mode")
    public void testFieldVisibilityByMode() {
        authFormPage = homePage.clickProfileButton();

        // In login mode - basic fields should be visible
        assertTrue(authFormPage.isEmailFieldVisible(),
                "Email field should be visible in login mode");
        assertTrue(authFormPage.isPasswordFieldVisible(),
                "Password field should be visible in login mode");
        assertTrue(authFormPage.isLoginSubmitButtonVisible(),
                "Login submit button should be visible in login mode");

        // Switch to register mode
        authFormPage.switchToRegisterMode()
                .waitForModeTransition();

        // In register mode - all fields should be visible
        assertTrue(authFormPage.isEmailFieldVisible(),
                "Email field should be visible in register mode");
        assertTrue(authFormPage.isPasswordFieldVisible(),
                "Password field should be visible in register mode");
        assertTrue(authFormPage.isRegisterSubmitButtonVisible(),
                "Register submit button should be visible in register mode");
    }

    @Test(priority = 7, groups = {"login"}, description = "Verify React animation transition timing")
    public void testAnimationTransitions() {
        authFormPage = homePage.clickProfileButton();

        long startTime = System.currentTimeMillis();

        // Switch modes and wait for transition
        authFormPage.switchToRegisterMode()
                .waitForModeTransition();

        long endTime = System.currentTimeMillis();
        long transitionTime = endTime - startTime;

        // Your framer-motion animation is 0.3s (300ms), so transition should be reasonable
        assertTrue(transitionTime >= 300 && transitionTime <= 1000,
                "Mode transition should take reasonable time (300-1000ms), actual: " + transitionTime + "ms");
    }

    @Test(priority = 8, groups = {"login"}, description = "Verify method chaining works with React component")
    public void testMethodChaining() {
        // Test the fluent interface with React component
        authFormPage = homePage.clickProfileButton()
                .switchToLoginMode()
                .waitForModeTransition()
                .enterEmail(LoginData.TestUser.EMAIL)
                .enterPassword(LoginData.TestUser.PASSWORD);

        // Verify we can still interact after chaining
        assertTrue(authFormPage.isInLoginMode(),
                "Should still be in login mode after method chaining");

        // Test chaining with mode switch
        authFormPage.switchToRegisterMode()
                .waitForModeTransition()
                .enterEmail(LoginData.Registration.EMAIL)
                .enterPassword(LoginData.Registration.PASSWORD);

        assertTrue(authFormPage.isInRegisterMode(),
                "Should be in register mode after chained mode switch");
    }

    @Test(priority = 9,groups = {"login"},  description = "Verify password mismatch handling in registration")
    public void testPasswordMismatchRegistration() {
        authFormPage = homePage.clickProfileButton()
                .switchToRegisterMode()
                .waitForModeTransition();

        // Attempt registration with mismatched passwords
        authFormPage.performRegistration(
                LoginData.Registration.EMAIL,
                LoginData.Registration.PASSWORD,
                LoginData.Registration.MISMATCHED_CONFIRM
        );

        // Verify error is displayed
        assertTrue(authFormPage.isErrorDisplayed(),
                "Error should be displayed for password mismatch");
    }

    @Test(priority = 10, groups = {"login"},  description = "Verify empty form validation")
    public void testEmptyFormValidation() {
        authFormPage = homePage.clickProfileButton();

        // Test empty login
        authFormPage.performLogin("", "");
        assertTrue(authFormPage.isErrorDisplayed(),
                "Error should be displayed for empty login credentials");

        // Test empty registration
        authFormPage.switchToRegisterMode()
                .waitForModeTransition()
                .performRegistration("", "", "");

        assertTrue(authFormPage.isErrorDisplayed(),
                "Error should be displayed for empty registration data");
    }
}