package JiraAuto;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.*;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class WebDriverTestBase {
    protected WebDriver browser1;
    protected WebDriver browser2;

    private HashMap<Integer, Integer> testResults = new HashMap<>();

    static {
        System.setProperty("webdriver.chrome.driver", "D:\\Nasik\\для Java\\chromedriver.exe");
    }
    @BeforeTest(alwaysRun = true)
    public void setUp() {
        browser1 = new ChromeDriver(new ChromeOptions().addArguments("--start-maximized", "--incognito"));
        browser2 = new ChromeDriver(new ChromeOptions().addArguments("--start-maximized", "--incognito"));

        browser1.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        browser2.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    @AfterTest(alwaysRun = true)
    public void finish() {
        browser1.close();
        browser2.close();
    }

//    @BeforeClass
//    public static void main(String[] args) throws Exception {
//
//        APIClient client = new APIClient
//                ("https://hillel5.testrail.io/");
//
//        client.setUser("rvalek@intersog.com");
//        client.setPassword("hillel");
//
//        Map data = new HashMap();
//        data.put("name", "QQQQQQQQ " + Helper.timeStamp());
//        data.put("include_all", false);
//
//        ArrayList<Integer> caseIds = new ArrayList<>();
//
//        caseIds.add(1);
//        caseIds.add(2);
//
//        data.put("case_ids", caseIds);
//
//        System.out.println("json" + JSONValue.toJSONString(data));
//
//        JSONObject c = (JSONObject) client.sendPost("add_run/1", data) ;
//
//        System.out.println(c.get("title"));
//    }

}
