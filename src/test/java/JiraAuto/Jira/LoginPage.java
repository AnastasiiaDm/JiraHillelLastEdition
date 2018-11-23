package JiraAuto.Jira;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import JiraAuto.Helper;

import java.util.List;

public class LoginPage {

//    private final By inputUsername = By.cssSelector("input#login-form-username");
//    private final By inputPassword = By.cssSelector("input#login-form-password");

    private final WebDriver browser;
    private final Helper h;

    @FindBy(css = "input#login-form-username")
    WebElement usernameInput;

    @FindBy(css = "input#login-form-password")
    WebElement passwordInput;
    @FindBy(css = "div#usernameerror")
    List<WebElement> errorMessages;

    @FindBy(css = "a#header-details-user-fullname")
    List<WebElement> buttonProfile;

    public LoginPage(WebDriver browser) {
        this.browser = browser;
        this.h = new Helper(browser);
    }
    private void login(boolean successful) {
        browser.get(JiraVars.baseURL);

        h.fill(usernameInput, JiraVars.username);
        h.fill(passwordInput, successful ? JiraVars.pass : JiraVars.badPass).submit();
    }
    public void successfulLogin() {
        login(true);
        Assert.assertEquals(JiraVars.username, buttonProfile.get(0).getAttribute(JiraVars.userNameCSS));
    }

    public void failureLogin() {
        login(false);
        Assert.assertTrue(errorMessages.size() != 0);
    }

}
