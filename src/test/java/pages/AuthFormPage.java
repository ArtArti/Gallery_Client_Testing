// AuthFormPage.java - Based on your React AuthForm component
package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AuthFormPage extends BasePage {

    // Toggle/Switch Button - This is the key element from your React component
    @FindBy(xpath = "//button[contains(text(), 'Switch to Register') or contains(text(), 'Switch to Login')]")
    private WebElement switchModeButton;

    // Form Card Container
    @FindBy(xpath = "//div[contains(@class, 'bg-white') and contains(@class, 'rounded-xl')]")
    private WebElement formCard;

    // Heading that changes based on mode
    @FindBy(xpath = "//h2[contains(text(), 'Login to Your Account') or contains(text(), 'Create a New Account')]")
    private WebElement formHeading;

    // Login Form Elements (when Login component is rendered)
    @FindBy(xpath = "//input[@type='email' or @name='email']")
    private WebElement emailField;

    @FindBy(xpath = "//input[@type='password' or @name='password']")
    private WebElement passwordField;

    @FindBy(xpath = "//button[(contains(text(), 'Login') or @id='loginBtn')]")
    private WebElement loginSubmitButton;

    // Register Form Elements (when Register component is rendered)
    @FindBy(xpath = "//input[@name='confirmPassword' or @id='confirmPassword']")
    private WebElement confirmPasswordField;

    @FindBy(xpath = "//input[@name='name' or @id='name']")
    private WebElement fullNameField;

    @FindBy(xpath = "//button[(contains(text(), 'Register') or contains(@class, 'w-full') or contains(text(), 'Create'))]")
    private WebElement registerSubmitButton;

    // Messages and Feedback
    @FindBy(xpath = "//div[contains(@class, 'bg-red-50')]")
    private WebElement errorMessage;

    @FindBy(xpath = "//div[contains(@class, 'bg-green-50')]")
    private WebElement successMessage;

    public boolean isInLoginMode() {
        String buttonText = getElementText(switchModeButton);
        return buttonText.contains("Switch to Register");
    }

    public boolean isInRegisterMode() {
        String buttonText = getElementText(switchModeButton);
        return buttonText.contains("Switch to Login");
    }

    public boolean isLoginHeadingDisplayed() {
        String headingText = getElementText(formHeading);
        return headingText.contains("Login to Your Account");
    }

    public boolean isRegisterHeadingDisplayed() {
        String headingText = getElementText(formHeading);
        return headingText.contains("Create a New Account");
    }

    // Mode Switching Methods
    public AuthFormPage switchToRegisterMode() {
        if (isInLoginMode()) {
            clickElement(switchModeButton);
            waitForRegisterMode();
        }
        return this;
    }

    public AuthFormPage switchToLoginMode() {
        if (isInRegisterMode()) {
            clickElement(switchModeButton);
            waitForLoginMode();
        }
        return this;
    }

    private void waitForLoginMode() {
        wait.until(driver -> isLoginHeadingDisplayed());
    }

    private void waitForRegisterMode() {
        // Wait for the heading to change to register
        wait.until(driver -> isRegisterHeadingDisplayed());
    }

    // Form Interaction Methods
    public AuthFormPage enterEmail(String email) {
        enterText(emailField, email);
        return this;
    }

    public AuthFormPage enterPassword(String password) {
        enterText(passwordField, password);
        return this;
    }

    public AuthFormPage enterConfirmPassword(String confirmPassword) {
        // Only enter if we're in register mode and field is visible
        if (isInRegisterMode() && isElementDisplayed(confirmPasswordField)) {
            enterText(confirmPasswordField, confirmPassword);
        }
        return this;
    }

    public AuthFormPage enterFullName(String fullName) {
        // Only enter if we're in register mode and field is visible
        if (isInRegisterMode() && isElementDisplayed(fullNameField)) {
            enterText(fullNameField, fullName);
        }
        return this;
    }

    public AuthFormPage clickLoginSubmit() {
        if (isInLoginMode()) {
            clickElement(loginSubmitButton);
        }
        return this;
    }

    public AuthFormPage clickRegisterSubmit() {
        if (isInRegisterMode()) {
            clickElement(registerSubmitButton);
        }
        return this;
    }

    // High-level Action Methods
    public AuthFormPage performLogin(String email, String password) {
        switchToLoginMode();
        enterEmail(email);
        enterPassword(password);
        clickLoginSubmit();
        return this;
    }

    public AuthFormPage performRegistration(String fullName, String email, String password, String confirmPassword) {
        switchToRegisterMode();

        if (isElementDisplayed(fullNameField)) {
            enterFullName(fullName);
        }
        enterEmail(email);
        enterPassword(password);

        if (isElementDisplayed(confirmPasswordField)) {
            enterConfirmPassword(confirmPassword);
        }

        clickRegisterSubmit();
        return this;
    }

    public AuthFormPage performRegistration(String email, String password, String confirmPassword) {
        return performRegistration("", email, password, confirmPassword);
    }

    // Validation Methods
    public boolean isFormCardVisible() {
        return isElementDisplayed(formCard);
    }

    public boolean isSwitchButtonVisible() {
        return isElementDisplayed(switchModeButton);
    }

    public String getSwitchButtonText() {
        return getElementText(switchModeButton);
    }

    public String getFormHeading() {
        return getElementText(formHeading);
    }

    public boolean isLoginSuccessful() {
        return isElementDisplayed(successMessage);
    }

    public boolean isErrorDisplayed() {
        return isElementDisplayed(errorMessage);
    }

    public String getErrorMessage() {
        return getElementText(errorMessage);
    }

    public boolean isEmailFieldVisible() {
        return isElementDisplayed(emailField);
    }

    public boolean isPasswordFieldVisible() {
        return isElementDisplayed(passwordField);
    }

    public boolean isConfirmPasswordFieldVisible() {
        return isElementDisplayed(confirmPasswordField);
    }

    public boolean isFullNameFieldVisible() {
        return isElementDisplayed(fullNameField);
    }

    public boolean isLoginSubmitButtonVisible() {
        return isElementDisplayed(loginSubmitButton);
    }

    public boolean isRegisterSubmitButtonVisible() {
        return isElementDisplayed(registerSubmitButton);
    }

    public AuthFormPage waitForModeTransition() {
        try {
            Thread.sleep(500); // 0.5 seconds
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return this;
    }
}