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

    public void projectSelect() {
        System.out.println("projectSelect start");
        createIssueButton.click();
        System.out.println("createButton size: " + createIssueButton.getText());

        h.fill(projectInput, JiraVars.projectName);
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
        dropInput.sendKeys(JiraVars.attachmentFileLocation + JiraVars.attachmentFileName);
    }


    public void waitSummarySubmit() {
        new FluentWait<>(browser)
                .withTimeout(Duration.ofSeconds(7))
                .pollingEvery(Duration.ofMillis(500))
                .ignoring(InvalidElementStateException.class)
                .until(browser -> h.fill(summaryInput, JiraVars.newIssueSummary))
                .submit();
    }

    public void waitLinkAttachment() {
        new FluentWait<>(browser)
                .withTimeout(Duration.ofSeconds(10))
                .pollingEvery(Duration.ofSeconds(2))
                .ignoring(NoSuchElementException.class)
                .until(browser -> linkAttachment);

        Assert.assertEquals(JiraVars.attachmentFileName, linkAttachment.getText());

        attachmentLink = linkAttachment.getAttribute("href");
    }

    public  void selectCheckBox(){
        checkBox1.click();

    }
}
