package com.cucumber.runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"pretty", "html:target/cucumber-reports.html", "json:target/surefire-reports/cucumber.json"},
        glue = {"com/cucumber/stepdefs",
                "com/cucumber/support"},
        features = {"src/test/resources"}
)
public class CucumberRunnerTest {


}
