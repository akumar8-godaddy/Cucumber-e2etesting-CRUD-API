package search;


import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;



@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"src/test/resources/feature"},
        plugin = {"pretty"}
       )
public class CrudRunnerTest {

}
