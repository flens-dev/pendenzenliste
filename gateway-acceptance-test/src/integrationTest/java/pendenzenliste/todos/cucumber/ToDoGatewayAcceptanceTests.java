package pendenzenliste.todos.cucumber;

import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * A runner class that can be used to execute the ToDo gateway acceptance tests.
 */
@Suite
@SelectClasspathResource("features/todos")
@CucumberContextConfiguration
@SpringBootTest(classes = ToDoGatewayAcceptanceTestConfig.class)
public class ToDoGatewayAcceptanceTests
{
}
