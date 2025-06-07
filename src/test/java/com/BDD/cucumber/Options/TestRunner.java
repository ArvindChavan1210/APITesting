package com.BDD.cucumber.Options;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.junit.runner.RunWith;


@CucumberOptions(
        features = "src/test/java/com/BDD/FeatureFiles",
        glue = {"com.BDD.StepDefinationFiles"},
        monochrome = true,  plugin = {"pretty", "html:target/cucumber-reports.html", "json:target/cucumber.json"}
        // tags = "@delete_placeAPI"

)
public class TestRunner extends AbstractTestNGCucumberTests {
}