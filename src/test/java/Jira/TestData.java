package Jira;

public interface TestData {
    String baseURL = "http://jira.hillel.it:8080/";

    String username = "autorob";
    String pass = "forautotests";
    String badPass = "baspass";

    String newIssueSummary = "AutoTest " + Helper.timeStamp();
    String attachmentFileLocation = "C:\\Users\\ASUS\\Desktop\\";
    String attachmentFileName = "JIRAlogo.jpg";
    String downloadedFileLocation = "C:\\Users\\ASUS\\Downloads";

    String userNameCSS = "data-username";
    String userName = "autorob";
    String projectName = "General QA Robert (GQR)\n";

    String date = "dd/MM/yy HH:mm";

}
