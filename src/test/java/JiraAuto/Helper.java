package JiraAuto;

import JiraAuto.Jira.JiraVars;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Helper {
    private WebDriver browser;

    public Helper(WebDriver currentBrowser){

        browser = currentBrowser;
    }

    public WebElement fill(WebElement selector, String value) {

        selector.sendKeys(value);
        return selector;
    }
    public static String timeStamp() {

        return new SimpleDateFormat(JiraVars.date).format(new Date());
    }
//    public WebElement findAndFill(By selector, String value) {
//        WebElement elem = browser.findElement(selector);
//
//        elem.sendKeys(value);
//        return elem;
//    }

}
