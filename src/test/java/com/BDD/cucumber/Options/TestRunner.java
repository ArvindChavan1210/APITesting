package com.BDD.cucumber.Options;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.junit.runner.RunWith;


@CucumberOptions(
        features = "src/test/java/com/BDD/FeatureFiles",
        glue = {"com.BDD.StepDefinationFiles"},
        plugin = {"json:target/jsonReports/cucumber-report.json"}
)

public class TestRunner extends AbstractTestNGCucumberTests {
}