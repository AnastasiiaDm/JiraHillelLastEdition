package JiraAuto.Jira;

import JiraAuto.WebDriverTestBase;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import JiraAuto.WebDriverTestBase;

public class JiraTests extends WebDriverTestBase {
    private LoginPage loginPage;
    private IssuePage issuePage;

    @BeforeClass(alwaysRun = true)
    public void initPages() {
        loginPage = PageFactory.initElements(browser, LoginPage.class);
        issuePage = PageFactory.initElements(browser, IssuePage.class);
        System.out.println("Jira Pages Initialized");
    }

    @Test(description = "1. Invalid Login", priority = -1)
    public void loginFail() {
        loginPage.failureLogin();
    }

    @Test(description = "2. Valid Login", groups = { "Sanity" })
    public  void loginSuccess() {
        loginPage.successfulLogin();
    }

    @Test(dependsOnMethods = {"loginSuccess"})
    public static void createIssue() {

        steps.projectSelect();

        steps.waitSummarySubmit();

        steps.createIssueCheck();
    }

    @Test(dependsOnMethods = {"createIssue"})
    public static void openIssue() {
        steps.openIssueCheck();
    }

    @Test(dependsOnMethods = {"openIssue"})
    public void uploadFile() {
        steps.uploadFileCheck();
        steps.waitLinkAttachment();
    }

    @Test(dependsOnMethods = {"uploadFile"})
    static void downloadFile() {

        browser.findElement(By.cssSelector("a.attachment-title")).submit();
//        browser.findElement(By.cssSelector("a#cp-control-panel-download")).click();

//        downloadedFileName =
//
//        Assert.assertEquals(JiraVars.downloadedFileLocation, );
    }


}
