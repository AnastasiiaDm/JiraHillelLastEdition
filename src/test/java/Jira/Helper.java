package Jira;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Helper {
    private WebDriver browser;

    Helper (WebDriver currentBrowser){

        browser = currentBrowser;
    }

    public  WebElement fill (WebElement selector, String value) {

        selector.sendKeys(value);
        return selector;
    }
    public static String timeStamp() {
        return new SimpleDateFormat(TestData.date).format(new Date());
    }

}
