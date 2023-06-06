package com.cucumber.support;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.restassured.RestAssured;


public class Hooks {

    @Before
    public void before(Scenario scenario) {
        RestAssured.baseURI = "https://petstore.swagger.io/v2/";
    }
}
