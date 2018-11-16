package Jira;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.FluentWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;

public class JiraActions {
    private String attachmentLink;
    private String newIssuePath;
    private String newIssueSummary = "AutoTest " + Helper.timeStamp();
    private WebDriver browser;
    private Helper h;

    @FindBy(css = "input#login-form-username")
    WebElement usernameInput;

    @FindBy(css = "input#login-form-password")
    WebElement passwordInput;

    @FindBy(css = "a#create_link")
    WebElement createIssueButton;

    @FindBy(css = "div#usernameerror")
    List<WebElement> errorMessages;

    @FindBy(css = "a#header-details-user-fullname")
    List<WebElement> buttonProfile;

    @FindBy(css = "input#project-field")
    WebElement projectInput;

    @FindBy(css = "input#summary")
    WebElement summaryInput;

    @FindBy(css = "a[class='issue-created-key issue-link']")
    List<WebElement> linkNewIssues;

    @FindBy(css = "input.issue-drop-zone__file")
    WebElement dropInput;

    @FindBy(css = "a.attachment-title")
    WebElement linkAttachment;

    @FindBy(css = "input.selectionCheckbox[name='selected-1']")
    WebElement checkBox1;



    public JiraActions(WebDriver browser) {
        this.browser = browser;
        this.h = new Helper(browser);
    }

    private void login(boolean correctPass) {

        String pass = (correctPass ? TestData.pass : TestData.badPass) + "\n";
        TestBase.browser.get(TestData.baseURL);

        h.fill(usernameInput, TestData.username);
        h.fill(passwordInput, pass);
    }

    public void loginFailCheck() {
        login(false);

        // Some error message is presentErrorMessages
        Assert.assertTrue(errorMessages.size() > 0);
        System.out.println("Login failed, error message present");

        // Logged-in UI is not present
        Assert.assertFalse(
                buttonProfile.size() > 0 && buttonProfile.get(0).getAttribute(TestData.userNameCSS).equals(TestData.userName));
        System.out.println("Login failed, username not present");
    }

    public void loginSuccessCheck() {
        login(true);
        Assert.assertTrue(
                buttonProfile.size() > 0 && buttonProfile.get(0).getAttribute(TestData.userNameCSS).equals(TestData.username));
        System.out.println("Profile.size: " + buttonProfile.size() + " Login Success");
    }

    public void projectSelect() {
        System.out.println("projectSelect start");
        createIssueButton.click();
        System.out.println("createButton size: " + createIssueButton.getText());

        h.fill(projectInput, TestData.projectName);
    }

    public void createIssueCheck() {

        Assert.assertTrue(linkNewIssues.size() != 0);

        newIssuePath = linkNewIssues.get(0).getAttribute("href");

        System.out.println("New issue Summary: " + newIssueSummary + "\n" + "createIssue success");
    }

    public void openIssueCheck() {
        browser.get(newIssuePath);
        Assert.assertTrue(browser.getTitle().contains(newIssueSummary));

        System.out.println("openIssue success");
    }

    public void uploadFileCheck() {
        dropInput.sendKeys(TestData.attachmentFileLocation + TestData.attachmentFileName);
    }


    public void waitSummarySubmit() {
        new FluentWait<>(browser)
                .withTimeout(Duration.ofSeconds(7))
                .pollingEvery(Duration.ofMillis(500))
                .ignoring(InvalidElementStateException.class)
                .until(browser -> h.fill(summaryInput, TestData.newIssueSummary))
                .submit();
    }

    public void waitLinkAttachment() {
        new FluentWait<>(browser)
                .withTimeout(Duration.ofSeconds(10))
                .pollingEvery(Duration.ofSeconds(2))
                .ignoring(NoSuchElementException.class)
                .until(browser -> linkAttachment);

        Assert.assertEquals(TestData.attachmentFileName, linkAttachment.getText());

        attachmentLink = linkAttachment.getAttribute("href");
    }

    public  void selectCheckBox(){
        checkBox1.click();

    }
}
