package com.lcarvalho.isaid.stepdefs;

import com.lcarvalho.isaid.config.SpringAcceptanceTest;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/acceptanceTest/resources/feature",
        extraGlue = "com.lcarvalho.isaid.commons")
public class CucumberTest {
}
