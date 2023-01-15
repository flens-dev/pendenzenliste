Feature: Delete todo

  As a user of the todo gateway
  I should be able to delete existing todos
  In order to remove them from the storage

  Scenario Outline: Todo does not exist - <backend>

    Given that I configure the application to use the '<backend>' todo gateway
    And that no todos exist
    And that I enter the todo id '42'

    When I try to delete the todo

    Then deleting the todo should have failed

    Examples:
      | backend    |
      | redis      |
      | inmemory   |
      | filesystem |

  Scenario Outline: Delete existing todo - <backend>

    Given that I configure the application to use the '<backend>' todo gateway
    And that the following todos exist:
      | identity | headline           | description | created                 | last modified           | completed | state |
      | 42       | Take out the trash |             | 2023-01-01T00:00:00.000 | 2023-01-01T00:00:00.000 |           | OPEN  |
    And that I enter the todo id '42'

    When I try to delete the todo

    Then deleting the todo should have succeeded

    Examples:
      | backend    |
      | redis      |
      | inmemory   |
      | filesystem |