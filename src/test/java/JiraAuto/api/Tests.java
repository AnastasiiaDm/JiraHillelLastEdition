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
    String userId = "";

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

    @Test(description = "Third requirement - edit and save users data")
    void saveUser() throws IOException {
        String data = "{\"id\":10,\"name\":\"TEST_ROBERT\",\"phone\":\"PHONEEE!!!!\",\"role\":\"Student\",\"strikes\":\"2\",\"location\":\"\"}";
        String userId = "10";

        System.out.println("saveUser:data " + data);
        System.out.println("saveUser:url " + baseURL + userId);
        String[] responseData = Requests.sendPut(baseURL + userId, data);

        String updatedInfo = Requests.sendGet(baseURL)[1];
        System.out.println(updatedInfo);

        Assert.assertTrue(updatedInfo.contains(data));
    }
    @Test(description = "Fourth requirement - creating users")
    void createUser() throws IOException {


        Assert.assertTrue();
    }

}
