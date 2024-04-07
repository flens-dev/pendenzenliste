Feature: Fetch todo

  As a user of the todo gateway
  I should be able to fetch an existing todo
  To further process the data stored with the todo.

  Scenario Outline: Todo does not exist - <backend>

    Given that I configure the application to use the '<backend>' todo gateway
    And that no todos exist
    And that I enter the todo id '42'

    When I try to fetch the todo

    Then I should have received no todo

    Examples:
      | backend       |
      | eclipse-store |
      | inmemory      |
      | filesystem    |
      | redis         |
      | postgresql    |

  Scenario Outline: Todo exists - <backend>

    Given that I configure the application to use the '<backend>' todo gateway
    And that the following todos exist:
      | identity | headline           | description | created                 | last modified           | completed | state |
      | 42       | Take out the trash |             | 2023-01-01T00:00:00.000 | 2023-01-01T00:00:00.000 |           | OPEN  |
    And that I enter the todo id '42'

    When I try to fetch the todo

    Then I should have received a todo
    And the todo should have the following data:
      | identity | headline           | description | created                 | last modified           | completed | state |
      | 42       | Take out the trash |             | 2023-01-01T00:00:00.000 | 2023-01-01T00:00:00.000 |           | OPEN  |

    Examples:
      | backend       |
      | eclipse-store |
      | inmemory      |
      | filesystem    |
      | redis         |
      | postgresql    |