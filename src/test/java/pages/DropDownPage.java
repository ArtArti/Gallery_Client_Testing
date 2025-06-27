package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;

public class DropDownPage extends BasePage {

    @FindBy(xpath = "//img[contains(@alt , 'User Avatar') or contains(@class, 'rounded-full')]")
    private WebElement avatarImage;

    @FindBy(id = "AvatarMenu")
    private WebElement dropdownMenu;

    @FindBy(xpath = "//button[contains(text(),'Contact')]")
    private WebElement contactLink;

    @FindBy(xpath = "//button[contains(text(),'Change Password')]")
    private WebElement changePassword;


    @FindBy(xpath = "//button[contains(text(),'Log Out')]")
    private WebElement logoutButton;

    public boolean isAvatarVisible() {
        return isElementDisplayed(avatarImage);
    }

    public void clickAvatar() {
        clickElement(avatarImage);
        waitForDropdownToLoad();
    }

    public boolean isDropdownVisible() {
        return isElementDisplayed(dropdownMenu);
    }

    private void waitForDropdownToLoad() {
        wait.until(driver -> isDropdownVisible());
    }

    public void clickChangePassword(){
        clickElement(changePassword);
    }

    public void clickContact() {
        clickElement(contactLink);
    }

    public void hoverOverMenuItem(String label) {
        WebElement menuItem = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//button[contains(., '" + label + "')]")
        ));
        Actions actions = new Actions(driver);
        actions.moveToElement(menuItem).pause(Duration.ofSeconds(1)).perform();
    }

    public void clickLogout() {
        wait.until(driver -> isElementDisplayed(logoutButton));
        clickElement(logoutButton);
    }
}
