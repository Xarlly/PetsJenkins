package com.cucumber.stepdefs;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.*;

public class PetsImplementation {

    public static Response postPet = null;
    private Response putPet = null;
    private Response deletePet = null;
    private Response getPetId = null;
    private Response getPetByStatus = null;

    private final String PETS_ENDPOINT = "pet/";
    private final String FIND_BY_STATUS_PATH = PETS_ENDPOINT + "findByStatus/";


    @Given("the post request that creates a new pet with name {string}")
    public void createPet(String petName) {
        HashMap<String, String> bodyRequestMap = new HashMap<>();
        bodyRequestMap.put("name", petName);
        postPet = given().contentType(ContentType.JSON).body(bodyRequestMap).post(PETS_ENDPOINT);
    }

    @Then("the response status code for the pet post is {int}")
    public void validatePostPetStatus(int code) {
        assertEquals("The response is not 200", code, postPet.statusCode());
    }

    @And("the body response contains a field id with numeric non negative value")
    public void validatePostPetId() {
        JsonPath postPetResponseJson = new JsonPath(postPet.body().asString());
        String idResponse = postPetResponseJson.getString("id");
        BigInteger idNumeric = new BigInteger(idResponse, 16);
        assertTrue("The id of the response is not correct",
                idNumeric.compareTo(new BigInteger("0")) >= 0);
    }

    @And("the body response for the pet post contains a field name with value {string}")
    public void validatePostPetName(String nameExpected) {
        JsonPath postPetResponseJson = new JsonPath(postPet.body().asString());
        String nameResponse = postPetResponseJson.getString("name");
        assertEquals("The name of the response is not correct", nameExpected, nameResponse);
    }

    @And("the put request that updates the pet created with name {string}")
    public void updatePet(String newName) {
        HashMap<String, String> bodyRequestMap = new HashMap<>();
        bodyRequestMap.put("name", newName);

        JsonPath postPetResponseJson = new JsonPath(postPet.body().asString());
        bodyRequestMap.put("id", postPetResponseJson.getString("id"));
        putPet = given().contentType(ContentType.JSON).body(bodyRequestMap).put(PETS_ENDPOINT);
    }

    @Then("the response status code for the pet put is {int}")
    public void validatePutPetStatus(int code) {
        assertEquals("The response is not 200", code, putPet.statusCode());
    }

    @And("the body response for the pet put contains a field name with value {string}")
    public void validatePutPetName(String nameExpected) {
        JsonPath postPetResponseJson = new JsonPath(putPet.body().asString());
        String nameResponse = postPetResponseJson.getString("name");
        assertEquals("The name of the response is not correct", nameResponse, nameExpected);
    }

    @And("the delete request that deletes the pet created")
    public void deletePet() {
        JsonPath postPetResponseJson = new JsonPath(postPet.body().asString());
        String petId = postPetResponseJson.getString("id");
        deletePet = given().contentType(ContentType.JSON).delete(PETS_ENDPOINT + petId);
    }

    @Then("the response status code for the delete is {int}")
    public void validateDeletePetStatus(int code) {
        assertEquals("The response is not 200", code, deletePet.statusCode());
    }

    @And("the body response for the delete contains a field message with value equal to the id of the deleted pet")
    public void validateDeletePetResponse() {
        JsonPath postPetResponseJson = new JsonPath(postPet.body().asString());
        String petId = postPetResponseJson.getString("id");

        JsonPath deletePetResponseJson = new JsonPath(deletePet.body().asString());
        String message = deletePetResponseJson.getString("message");
        assertEquals("Value of field message in delete response is incorrect", petId, message);
    }


    @And("the get request that finds the pet created by its id")
    public void getPetById() {
        JsonPath postPetResponseJson = new JsonPath(postPet.body().asString());
        String petId = postPetResponseJson.getString("id");

        getPetId = given().contentType(ContentType.JSON).get(PETS_ENDPOINT + petId);
    }

    @Then("the response status code for the get pet by id is {int}")
    public void validateGetPetByIdStatus(int code) {
        assertEquals("The response is not 200", code, getPetId.statusCode());
    }


    @And("the body response for the get pet by id contains a field name with value {string}")
    public void validateGetPetByIdBody(String expectedName) {
        JsonPath getPetResponseJson = new JsonPath(getPetId.body().asString());
        String actualName = getPetResponseJson.getString("name");

        assertEquals("Value of field message in delete response is incorrect", expectedName, actualName);
    }

    @Given("the get request that finds pets by status {string}")
    public void getPetByStatus(String petStatus) {
        getPetByStatus = given().log().all().param("status", petStatus).get(FIND_BY_STATUS_PATH);
    }

    @Then("the response status code for the get pet by status is {int}")
    public void validateGetPetByStatusCode(int code) {
        assertEquals("The response is not 200", code, getPetByStatus.statusCode());
    }

    @And("the response body contains a list of pets with status {string}")
    public void validateGetPetByStatusBody(String petStatus) {
        JsonPath getPetResponseJson = new JsonPath(getPetByStatus.body().asString());
        List<String> petStatusList = getPetResponseJson.getList("status");
        for (String pS : petStatusList) {
            if (!pS.equals(petStatus)) {
                fail("Pet does not have status " + petStatus);
            }
        }
    }

}
