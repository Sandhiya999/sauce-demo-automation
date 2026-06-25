package cucumber;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;
@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources",
        glue = {"cucumber"},
        plugin = {"pretty","html:target/cucumber-report.html",
        "json:target/cucumber.json",
                "junit:target/cucumber.xml"}
)

public class runner {

}
