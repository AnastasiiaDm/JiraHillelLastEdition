package JiraAuto.api;


import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import JiraAuto.api.requests.Requests;

public class Tests {
    String baseURL = "http://37.59.228.229:3000/API/users/";
    String userId = "1";

    private void findUserID(String data) {
        Matcher m = Pattern.compile("\"id\":\"(\\d+)").matcher(data);
        if (m.find())
            userId = m.group(1);
    }

    private void checkContentType(String headers) {
        Assert.assertTrue(headers.contains("Content-Type: text/html"));
    }

    @Test(description = "Second requirement - getting user list")
    void getUsers() throws IOException {
        String[] responseData = Requests.sendGet(baseURL);
        for (String string : responseData)
            System.out.println("response " + string);
        Assert.assertTrue(responseData[1].contains("[{\"name\":\""));
        findUserID(responseData[1]);
        checkContentType(responseData[0]);
    }

    @DataProvider
    public Object[][] saveUserData() {
        return new Object[][]{{"\"role\": \"Administrator\"", true}, {"sadadd", false}};
    }

    @Test(description = "Third requirement - saving users", dataProvider = "saveUserData")
    void saveUser(String data, Boolean expectedResult) throws IOException {
        System.out.println("saveUser:data " + data);
        System.out.println("saveUser:url " + baseURL + userId);
        String[] responseData = Requests.sendPut(baseURL + userId, '{' + data + '}');
        Assert.assertEquals((Boolean) Requests.getUserInfo(baseURL, userId).contains(data), expectedResult);
        checkContentType(responseData[0]);
    }

}
