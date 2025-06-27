// HomePage.java - Home page with profile button
package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage {

    @FindBy(xpath = "//span[contains(text(),'Profile') or @id='profile']")
    private WebElement profileButton;

    public AuthFormPage clickProfileButton() {
        clickElement(profileButton);
        return new AuthFormPage();
    }

    public boolean isProfileButtonVisible()
    {
        return isElementDisplayed(profileButton);
    }
}
