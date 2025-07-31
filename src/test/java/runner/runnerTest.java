package runner;

import org.testng.annotations.DataProvider;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src/test/resources/features", glue = { "stepdefinitions" , "Hooks"}, plugin = {
		"pretty", "html:test-output/cucumber-reports/cucumber-pretty",
		"json:test-output/cucumber-reports/CucumberTestReport.json",
		"rerun:test-output/cucumber-reports/rerun.txt" }, monochrome = true)
public class runnerTest extends AbstractTestNGCucumberTests {
	@Override
    @DataProvider(parallel = false)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}