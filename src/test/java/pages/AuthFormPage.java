// ProfilePage.java - Profile page with login/register options
package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ProfilePage extends BasePage {

    @FindBy(xpath = "//button[contains(text(),'Switch to Login') or @id='login-btn' or @class*='login']")
    private WebElement loginButton;

    @FindBy(xpath = "//button[contains(text(),'Switch to Register') or @id='register-btn' or @class*='register']")
    private WebElement registerButton;

    public LoginPage clickLoginButton() {
        clickElement(loginButton);
        return new LoginPage();
    }

    public RegisterPage clickRegisterButton() {
        clickElement(registerButton);
        return new RegisterPage();
    }

    public boolean isLoginButtonVisible() {
        return isElementDisplayed(loginButton);
    }

    public boolean isRegisterButtonVisible() {
        return isElementDisplayed(registerButton);
    }
}
