package com.lcarvalho.isaid;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/acceptanceTest/resources/feature")
public class CucumberTest {
}
