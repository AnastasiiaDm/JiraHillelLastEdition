package JiraAuto.api;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
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
        String data = "{\"id\":10,\"name\":\"TEST_ROBERT\",\"phone\":\"PHONEEE!!!!\",\"role\":\"Student\"}";
        String userId = "10";


        System.out.println("saveUser:data " + data);
        System.out.println("saveUser:url " + baseURL + userId);
        String[] responseData = Requests.sendPut(baseURL + userId, data);
        System.out.println(responseData);

        String updatedInfo = Requests.sendGet(baseURL)[1];
        System.out.println(updatedInfo);

        Assert.assertTrue(updatedInfo.contains(data));
    }
    @Test(description = "Fourth requirement - creating users")
    void createUser() throws IOException, ParseException {
//
        JSONArray array = new JSONArray();
        JSONObject item = new JSONObject();
        item.put("name", "Nasik_Student_noRole");
        item.put("phone", "blabla");
        array.add("items massive = " + item);
        System.out.println(item);
        String[] responseData = Requests.sendPost(baseURL, item.toString());

        System.out.println("responseData0 = " + responseData[0]);
        System.out.println("responseData1 = " + responseData[1]);

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject)jsonParser.parse(responseData[1]);
        Long newID = (Long) jsonObject.get("id");
        System.out.println("new ID = " + newID);

        String updatedInfo = Requests.sendGet(baseURL)[1];
        System.out.println("updatedInfo: " + updatedInfo);

        Assert.assertTrue(updatedInfo.contains(newID.toString()));
    }
    @Test(description = "Fifth requirement - edit users admin role")
    void adminUser() throws IOException {
        String data = "{\"role\":\"Administrator\",\"phone\":\"blabla\",\"name\":\"Nasik_Java\",\"id\":94}";
        String refreshAdmins = "refreshAdmins";

        System.out.println("saveUser:data " + data);
        System.out.println("saveUser:url " + baseURL + refreshAdmins);
        String[] responseData = Requests.sendPut(baseURL + refreshAdmins, data);
        System.out.println("responseData: " + responseData);

        String updatedInfo = Requests.sendGet(baseURL)[1];
        System.out.println("updatedInfo: " + updatedInfo);

//        not sure with valid assert. What's difference between simple role edit and edit with "refreshAdmins" ?
        Assert.assertTrue(updatedInfo.contains(data));
    }

    @Test(description = "Sixth requirement - creating admin")
    void createAdmin() throws IOException, ParseException {
//
        JSONArray array = new JSONArray();
        JSONObject item = new JSONObject();
        item.put("name", "Nasik_Administrator");
        item.put("phone", "blabla");
        item.put("role", "Administrator");
        array.add("items massive = " + item);
        System.out.println("item: " + item);
        String[] responseData = Requests.sendPost(baseURL, item.toString());

        System.out.println("responseData0 = " + responseData[0]);
        System.out.println("responseData1 = " + responseData[1]);

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject)jsonParser.parse(responseData[1]);
        Long newID = (Long) jsonObject.get("id");
        System.out.println("new ID = " + newID);

        String updatedInfo = Requests.sendGet(baseURL)[1];
        System.out.println("updatedInfo: " + updatedInfo);

        Assert.assertTrue(updatedInfo.contains(newID.toString()));
        Assert.assertTrue(updatedInfo.contains(responseData[1]));
    }
    @Test(description = "Seventh requirement - creating admin")
    void deleteUser() throws IOException{
        userId = "110";

        String[] responseData = Requests.sendDelete(baseURL + userId );

        System.out.println("responseData1: " + responseData[1]);

        String updatedInfo = Requests.sendGet(baseURL)[1];
        System.out.println("updatedInfo: " + updatedInfo);

        Assert.assertFalse(updatedInfo.contains("\"id\":" + userId));

    }


}
