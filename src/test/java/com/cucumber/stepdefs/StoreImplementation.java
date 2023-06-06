package com.cucumber.stepdefs;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasKey;
import static org.junit.Assert.*;

public class StoreImplementation {
    private Response orderPost = null;
    private Response orderDelete = null;
    private Response orderGet = null;
    private Response inventoriesGet = null;

    private final String STORE_ENDPOINT = "store/";
    private final String ORDER_PATH = STORE_ENDPOINT + "order/";
    private final String INVENTORY_PATH = STORE_ENDPOINT + "inventory/";


    @And("the post request that places an order for purchasing the pet created")
    public void orderPet() {
        JsonPath postPetResponseJson = new JsonPath(PetsImplementation.postPet.body().asString());
        String petId = postPetResponseJson.getString("id");

        HashMap<String, String> bodyRequestMap = new HashMap<>();
        bodyRequestMap.put("petId", petId);
        bodyRequestMap.put("status", "placed");
        bodyRequestMap.put("complete", "true");

        orderPost = given().contentType(ContentType.JSON).body(bodyRequestMap).post(ORDER_PATH);
    }

    @Then("the response status code for the post order is {int}")
    public void validatePostOrderStatusCode(int code) {
        assertEquals("Status code is not 200", code, orderPost.statusCode());
    }

    @And("the body response for the post order contains a field status with value {string}")
    public void validatePostOrderBodyResponse(String value) {
        JsonPath postPetResponseJson = new JsonPath(orderPost.body().asString());
        String orderStatus = postPetResponseJson.getString("status");
        assertEquals("Status of the order is not placed", value, orderStatus);
    }

    @When("the user sends the delete request that removes the order created")
    public void deleteOrder() {
        JsonPath postOrderResponseJson = new JsonPath(orderPost.body().asString());
        String id = postOrderResponseJson.getString("id");

        orderDelete = given().delete(ORDER_PATH + id);
    }

    @Then("the response status code for the delete order is {int}")
    public void validateDeleteOrderStatusCode(int code) {
        assertEquals("Status code is not 200", code, orderDelete.statusCode());
    }

    @And("the body response contains a field message with value equal to the id of the order created")
    public void validateDeleteOrderResponse() {
        JsonPath postOrderResponseJson = new JsonPath(orderPost.body().asString());
        String orderId = postOrderResponseJson.getString("id");

        JsonPath deleteOrderResponseJson = new JsonPath(orderDelete.body().asString());
        String orderMessage = deleteOrderResponseJson.getString("message");

        assertEquals("Message of the response is not equal to id of the order", orderId, orderMessage);
    }

    @When("the user sends the get request that finds the order placed")
    public void findOrder() {
        JsonPath postOrderResponseJson = new JsonPath(orderPost.body().asString());
        String id = postOrderResponseJson.getString("id");

        orderGet = given().get(ORDER_PATH + id);
    }


    @Then("the response status code for the get order is {int}")
    public void validateGetOrderStatusCode(int code) {
        assertEquals("Status code is not 200", code, orderGet.statusCode());
    }

    @And("the body response contains a field id with value equal to the order id")
    public void validateGetOrderResponse() {
        JsonPath postOrderResponseJson = new JsonPath(orderPost.body().asString());
        String id = postOrderResponseJson.getString("id");

        JsonPath getOrderResponseJson = new JsonPath(orderGet.body().asString());
        String idGet = getOrderResponseJson.getString("id");

        assertEquals("Ids do not match", id, idGet);
    }

    @Given("the get request that returns pet inventories by status")
    public void getInventories() {
        inventoriesGet = given().get(INVENTORY_PATH);
    }

    @Then("the response status code for the get inventories is {int}")
    public void valiadteGetInventoriesStatusCode(int code) {
        assertEquals("Status code is not 200", code, inventoriesGet.statusCode());
    }

    @And("the body response contains a field named {string}")
    public void valiadateGetInventoriesResponse(String fieldName) {
        inventoriesGet.then().body("$", hasKey(fieldName));
    }
}
