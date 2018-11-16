package Jira;

import TestRail.APIClient;
import org.json.simple.JSONObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static java.lang.System.setProperty;

public class TestBase {
    public static WebDriver browser;
    public static Helper h;

    @BeforeTest
    public static void openBrowser() {
        setProperty("webdriver.chrome.driver", "D:\\Nasik\\для Java\\chromedriver.exe");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");

        browser = new ChromeDriver();
        browser.manage().window().maximize();
        browser.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
    }

    @AfterTest
    public static void closeBrowser() {
        browser.quit();

    }

    @BeforeGroups(groups = { "Attachments" })
    public static void prepareAttachment() throws IOException {
        new File(TestData.attachmentFileName).createNewFile();
    }

    @AfterMethod
    public static void reportDebugInfo(ITestResult testResult) {
        String info = "";
        info += testResult.getMethod().getDescription();
        info += ": ";
        info += testResult.isSuccess() ? "passed." : "failed.";

        System.out.println(info);
    }

    @BeforeClass
    public static void main(String[] args) throws Exception {

//        APIClient client = new APIClient("http://<server>/testrail/");
        APIClient client = new APIClient
                ("https://hillel5.testrail.io/");

        client.setUser("rvalek@intersog.com");
        client.setPassword("hillel");

        Map data = new HashMap();
        data.put("name", "QQQQQQQQ");
        data.put("include_all", false);

        JSONObject c = (JSONObject) client.sendPost("add_run/1", data) ;
        ArrayList<Integer> caseIds = new ArrayList<>();

        caseIds.add(1);
        caseIds.add(2);
        c.put("case_ids", caseIds);

        System.out.println(c.get("title"));



    }

}
