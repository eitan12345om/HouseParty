package com.houseparty.houseparty.cucumber;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

/**
 * @author Eitan created on 4/20/2018.
 * <p>
 * For a tutorial about Cucumber, go to:
 * http://www.srccodes.com/p/article/48/cucumber-test-behavior-driven-development-bdd-feature-step-runner-glue-gherkin-data-table-scenario-given-when-then
 */
@RunWith(Cucumber.class)
@CucumberOptions(
    format = {
        "pretty",
        "json:src/test/java/com/houseparty/houseparty/cucumber/Cucumber.json",
        "html:src/test/java/com/houseparty/houseparty/cucumber/Cucumber.html"
    },
    glue = {
        "src/test/java/com/houseparty/houseparty/cucumber_steps"
    },
    features = {
        "src/test/java/com/houseparty/houseparty/features"
    }
)
public class CucumberRunner {
    @BeforeClass
    public static void setup() {
        return;
    }

    @AfterClass
    public static void teardown() {
        return;
    }
}
