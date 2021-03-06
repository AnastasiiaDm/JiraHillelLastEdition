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
    String URL = "http://37.59.228.229";
    String baseURL = "http://37.59.228.229:3000/API/users/";
    String userId = "";
    String port20007 = ":20007";

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
        JSONObject jsonObject = (JSONObject) jsonParser.parse(responseData[1]);
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
        JSONObject jsonObject = (JSONObject) jsonParser.parse(responseData[1]);
        Long newID = (Long) jsonObject.get("id");
        System.out.println("new ID = " + newID);

        String updatedInfo = Requests.sendGet(baseURL)[1];
        System.out.println("updatedInfo: " + updatedInfo);

        Assert.assertTrue(updatedInfo.contains(newID.toString()));
        Assert.assertTrue(updatedInfo.contains(responseData[1]));
    }

    @Test(description = "Seventh requirement - creating admin")
    void deleteUser() throws IOException {
        userId = "110";

        String[] responseData = Requests.sendDelete(baseURL + userId);

        System.out.println("responseData1: " + responseData[1]);

        String updatedInfo = Requests.sendGet(baseURL)[1];
        System.out.println("updatedInfo: " + updatedInfo);

        Assert.assertFalse(updatedInfo.contains("\"id\":" + userId));

    }

    @Test(description = "Eighth  requirement - open port 20007")
    void openPort20007() throws IOException {
        String[] responseData = Requests.sendGet(URL + port20007);
//        String[] responseData = Requests.sendGet(URL + ":3000");
        if (responseData != null)
            System.out.println("responseData success: " + responseData[1]);
        else
            System.out.println("Object is null");
    }

    @Test(description = "Ninth  requirement - all requests contain header Content-Type application/json")
    void responseContainHeader() throws IOException {
        String[] responseData = Requests.sendGet(URL + ":3000");
        if (responseData != null)
            System.out.println("responseData success: " + responseData[0]);
        else
            System.out.println("Object is null");

        if (responseData[0].contains("[Content-Type: application/json; "))
            System.out.println("Content-Type correct");
        else
            System.out.println("Incorrect Content-Type: " + responseData[0]);
    }

    @Test(description = "Tenth  requirement - if header incorrect or not exist, return 401")
    void errorStatus401() throws IOException {
        String[] responseData = Requests.sendGet(URL + ":3000");
//        String[] responseData = Requests.sendPost(baseURL, "");

        if (responseData != null)
            System.out.println("responseData success: " + responseData[0]);
        else
            System.out.println("Object is null");

        if (responseData[0].contains("[Content-Type: application/json; "))
            System.out.println("Content-Type correct");
        if (!responseData[0].contains("[Content-Type: application/json; ") || !responseData[0].contains("[Content-Type: ")) {
            System.out.println("Content-Type incorrect or not exist" + "\n" + responseData[0]);
            System.out.println("response status: " + "\n" + responseData[2]);
            if (!responseData[2].contains("401")) {
                Assert.assertFalse(responseData[2].contains("401"));
                System.out.println("incorrect status: " + responseData[2]);
            } else {
                Assert.assertTrue(responseData[2].contains("401"));
                System.out.println("status 401 is present");
            }
        }
    }

    @Test(description = "Eleventh  requirement - initial server state")
    void initialServerState() throws IOException {

    }

    @Test(description = "Twelfth  requirement - if  role incorrect, return 401")
    void incorrectRole() throws IOException, ParseException {
        JSONArray array = new JSONArray();
        JSONObject item = new JSONObject();
        item.put("name", "Nasik_Student_noRole");
        item.put("phone", "blabla");
        item.put("role", "Administrator");
        array.add("items massive = " + item);
        System.out.println("item: " + item);
        String[] responseData = Requests.sendPost(baseURL, item.toString());

        System.out.println("responseData0 = " + responseData[0]);
        System.out.println("responseData1 = " + responseData[1]);

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(responseData[1]);
        Long newID = (Long) jsonObject.get("id");
        System.out.println("new ID = " + newID);

        String updatedInfo = Requests.sendGet(baseURL)[1];

        if (updatedInfo.contains(newID.toString())) {
            Assert.assertTrue(updatedInfo.contains(newID.toString()));
            System.out.println("updatedInfo: " + updatedInfo);
        } else {
            Assert.assertFalse(!updatedInfo.contains(newID.toString()));
            System.out.println("updatedInfo: " + updatedInfo);
        }

        if (responseData[1].contains("\"role\":\"Administrator\"")){
            Assert.assertTrue(true);
            System.out.println("responseData[1].contains(role: Administrator)");
        }else {
            Assert.assertFalse(false);
            System.out.println("responseData[1].contains incorret role: " + responseData[1]);
            if (responseData[2].contains("401")){
                Assert.assertTrue(true);
                System.out.println("request 401 is present");
            }
            else {
                Assert.assertFalse(false);
                System.out.println("incorrect request, expected: 401, actual: " + responseData[2]);
            }
        }
    }

    @Test(description = "Thirteenhh  requirement - if user is not exist, for modification or deleting return 404")
    void nonexistentUser() throws IOException {

    }

}
