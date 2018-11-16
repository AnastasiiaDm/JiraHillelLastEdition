package Jira;

import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class JiraTests extends TestBase {
    static JiraActions steps;

    @BeforeTest
    public static void init() {
        steps = PageFactory.initElements(browser, JiraActions.class);
    }

    @Test(priority = -1)
    public static void loginFail() {

        steps.loginFailCheck();
    }

    @Test(priority = 1)
    public static void loginSuccess() {

        steps.loginSuccessCheck();
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
//        Assert.assertEquals(TestData.downloadedFileLocation, );
    }


}
