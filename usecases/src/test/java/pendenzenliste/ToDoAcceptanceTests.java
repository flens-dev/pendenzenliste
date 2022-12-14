package pendenzenliste;

import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

/**
 * A runner class that can be used to execute the ToDo acceptance tests.
 */
@Suite
@SelectClasspathResource("features")
public class ToDoAcceptanceTests
{
}
