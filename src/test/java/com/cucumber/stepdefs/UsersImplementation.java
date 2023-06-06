package com.cucumber.stepdefs;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.io.File;
import java.math.BigInteger;
import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.*;

public class UsersImplementation {
    private Response postUser = null;
    private String usernameRequest = null;
    private String passwordRequest = null;
    private Response postUsersArray = null;
    private Response postUsersList = null;
    private Response putUser = null;
    private Response deleteUser = null;
    private Response getLogin = null;
    private Response getLogout = null;
    private Response getUser = null;

    private final String USERS_ARRAY_FILE = "src/test/resources/data/usersArrayData.json";
    private final String USERS_LIST_FILE = "src/test/resources/data/usersListData.json";

    private final String USERS_ENDPOINT = "user/";
    private final String CREATE_WITH_ARRAY_PATH = USERS_ENDPOINT + "createWithArray/";
    private final String CREATE_WITH_LIST_PATH = USERS_ENDPOINT + "createWithList/";
    private final String LOGIN_PATH = USERS_ENDPOINT + "login/";
    private final String LOGOUT_PATH = USERS_ENDPOINT + "logout/";



    @Given("the post request that creates a new user with username {string}")
    public void createUser(String username) {
        HashMap<String, String> bodyRequestMap = new HashMap<>();
        bodyRequestMap.put("username", username);
        postUser = given().contentType(ContentType.JSON).body(bodyRequestMap).post(USERS_ENDPOINT);
        usernameRequest = username;
    }

    @Then("the response status code for the post user is {int}")
    public void validateCreateUserStatusCode(int code) {
        assertEquals("Status code is not 200", code, postUser.statusCode());
    }

    @And("the body response contains a field message with a numeric non negative value")
    public void validateCreateUserResponse() {
        JsonPath postUserResponseJson = new JsonPath(postUser.body().asString());
        String idResponse = postUserResponseJson.getString("message");
        BigInteger idNumeric = new BigInteger(idResponse, 16);

        assertTrue("The id of the response is not correct",
                idNumeric.compareTo(new BigInteger("0")) >= 0);
    }

    @Given("the post request that creates a list of user with an array")
    public void createUsersWithArray() {
        File file = new File(USERS_ARRAY_FILE);
        postUsersArray = given().contentType(ContentType.JSON).body(file).post(CREATE_WITH_ARRAY_PATH);
    }

    @Then("the response status code for the create users with array post is {int}")
    public void validateCreateWithArrayStatusCode(int code) {
        assertEquals("Status code is not 200", code, postUsersArray.statusCode());
    }

    @And("the body response for the create with array contains a field message with value {string}")
    public void validateCreateWithArrayResponse(String value) {
        JsonPath postArrayResponseJson = new JsonPath(postUsersArray.body().asString());
        String message = postArrayResponseJson.getString("message");
        assertEquals("Message is not ok", value, message);
    }

    @Given("the post request that creates a list of user with a list")
    public void createUsersWithList() {
        File file = new File(USERS_LIST_FILE);
        postUsersList = given().contentType(ContentType.JSON).body(file).post(CREATE_WITH_LIST_PATH);
    }


    @Then("the response status code for the create users with list post is {int}")
    public void validateCreateWithListStatusCode(int code) {
        assertEquals("Status code is not 200", code, postUsersList.statusCode());
    }


    @And("the body response for the create with list contains a field message with value {string}")
    public void validateCreateWithListResponse(String value) {
        JsonPath postArrayResponseJson = new JsonPath(postUsersList.body().asString());
        String message = postArrayResponseJson.getString("message");
        assertEquals("Message is not ok", value, message);
    }

    @And("the put request that updates the user created with name {string}")
    public void updateUser(String username) {
        HashMap<String, String> bodyRequestMap = new HashMap<>();
        bodyRequestMap.put("username", username);

        putUser = given().contentType(ContentType.JSON).body(bodyRequestMap).put(USERS_ENDPOINT + usernameRequest);
    }

    @Then("the response status code for the user put is {int}")
    public void validateUpdateUserStatusCode(int code) {
        assertEquals("Status code is not 200", code, putUser.statusCode());
    }


    @And("the body response for the put contains a field message with a numeric non negative value")
    public void validateUpdateUserResponse() {
        JsonPath putUserResponseJson = new JsonPath(putUser.body().asString());
        String responseMessage = putUserResponseJson.getString("message");
        BigInteger messageNumeric = new BigInteger(responseMessage, 16);

        assertTrue("The id of the response is not correct",
                messageNumeric.compareTo(new BigInteger("0")) >= 0);
    }

    @And("the delete request that deletes the user created")
    public void deleteUser() {
        deleteUser = given().delete(USERS_ENDPOINT + usernameRequest);
    }


    @Then("the response status code for the delete user is {int}")
    public void validateDeleteUserStatusCode(int code) {
        assertEquals("Status code is not 200", code, deleteUser.statusCode());
    }

    @And("the body response contains a field message with value equal to {string}")
    public void validateDeleteUserResponse(String value) {
        JsonPath putUserResponseJson = new JsonPath(deleteUser.body().asString());
        String responseMessage = putUserResponseJson.getString("message");

        assertEquals("Message not correct", value, responseMessage);
    }

    @Given("the post request that creates a new user with username {string} and password {string}")
    public void createUserWithPassword(String username, String password) {
        HashMap<String, String> bodyRequestMap = new HashMap<>();
        bodyRequestMap.put("username", username);
        bodyRequestMap.put("password", password);
        postUser = given().contentType(ContentType.JSON).body(bodyRequestMap).post("user");
        usernameRequest = username;
        passwordRequest = password;
    }


    @When("the user sends the get request that logs the user into the system")
    public void getLogin() {
        HashMap<String, String> paramsRequestMap = new HashMap<>();
        paramsRequestMap.put("username", usernameRequest);
        paramsRequestMap.put("password", passwordRequest);

        getLogin = given().contentType(ContentType.JSON).params(paramsRequestMap).get(LOGIN_PATH);
    }

    @Then("the response status code for the get login is {int}")
    public void validateLoginStatusCode(int code) {
        assertEquals("Status code is not 200", code, getLogin.statusCode());
    }

    @And("the user sends the get request that logs out the session")
    public void getLogout() {
        getLogout = given().get(LOGOUT_PATH);
    }

    @Then("the response status code for the logout get is {int}")
    public void validateLogoutStatusCode(int code) {
        assertEquals("Status code is not 200", code, getLogout.statusCode());
    }

    @When("the user sends the get request that returns the user with username {string}")
    public void getUserByUsername(String username) {
        getUser = given().get(USERS_ENDPOINT + username);
    }

    @Then("the response status code for the get user is {int}")
    public void validateGetUserStatusCode(int code) {
        assertEquals("Status code is not 200", code, getUser.statusCode());
    }

    @And("the response body contains a field username with value {string}")
    public void validateGetUserResponse(String username) {
        JsonPath getUserResponseJson = new JsonPath(getUser.body().asString());
        String usernameResponse = getUserResponseJson.getString("username");
        assertEquals("Field username not correct", username, usernameResponse);
    }
}
