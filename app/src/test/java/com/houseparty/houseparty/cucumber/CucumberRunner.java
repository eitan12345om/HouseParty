package com.houseparty.houseparty.cucumber;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

/**
 * @author Eitan created on 4/20/2018.
 */
@RunWith(Cucumber.class)
@CucumberOptions(
        format = {"pretty", "json:target/"},
        features = {"src/test/java/com/houseparty/houseparty/features"}
)
public class CucumberRunner {
}
