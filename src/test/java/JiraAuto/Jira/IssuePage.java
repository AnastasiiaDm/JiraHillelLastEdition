package JiraAuto.Jira;

import JiraAuto.Helper;
import JiraAuto.WebDriverTestBase;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.FluentWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;

public class IssuePage {
    private String attachmentLink;
    private String newIssuePath;
    private String newIssueSummary = "AutoTest " + Helper.timeStamp();
    private final WebDriver browser;
    private final Helper h;

    @FindBy(css = "a#create_link")
    WebElement createIssueButton;

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

    public IssuePage(WebDriver browser) {
        this.browser = browser;
        this.h = new Helper(browser);
    }

    public boolean errorMessageIsShown(){
        return this.linkNewIssues.size() != 0;
    }

    public void createIssue() {
        System.out.println("projectSelect start");
        createIssueButton.click();
        System.out.println("createButton size: " + createIssueButton.getText());

        h.fill(projectInput, JiraVars.projectName);

        new FluentWait<>(browser).withTimeout(Duration.ofSeconds(5)).pollingEvery(Duration.ofMillis(500))
                .ignoring(InvalidElementStateException.class)
                .until(browser -> h.fill(summaryInput, JiraVars.newIssueSummary)).submit();

        // ((JavascriptExecutor) browser).executeScript("window.scrollBy(0,250)");

        Assert.assertTrue(linkNewIssues.size() != 0);

        newIssuePath = linkNewIssues.get(0).getAttribute("href");

        System.out.println("New issue Summary: " + newIssueSummary + "\n" + "createIssue success");
    }

    public void openIssue() {
        browser.get(newIssuePath);
        Assert.assertTrue(browser.getTitle().contains(newIssueSummary));

        System.out.println("openIssue success");
    }

    public void uploadFile() {
        dropInput.sendKeys(JiraVars.attachmentFileLocation + JiraVars.attachmentFileName);

        new FluentWait<>(browser)
                .withTimeout(Duration.ofSeconds(10))
                .pollingEvery(Duration.ofSeconds(2))
                .ignoring(NoSuchElementException.class)
                .until(browser -> linkAttachment);

        Assert.assertEquals(JiraVars.attachmentFileName, linkAttachment.getText());

        attachmentLink = linkAttachment.getText();
    }

    public  void downloadFile(){
        browser.get(attachmentLink);
    }
}
