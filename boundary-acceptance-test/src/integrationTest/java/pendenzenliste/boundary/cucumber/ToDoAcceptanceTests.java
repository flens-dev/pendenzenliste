package pendenzenliste.boundary.cucumber;

import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * A runner class that can be used to execute the ToDo acceptance tests.
 */
@Suite
@SelectClasspathResource("features")
@CucumberContextConfiguration
@SpringBootTest(classes = TestConfig.class)
public class ToDoAcceptanceTests
{
}
