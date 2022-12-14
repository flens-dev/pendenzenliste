Feature: Complete ToDo

  As a user
  I should be able to complete a ToDo
  In order to remove it from my open ToDos.

  Scenario: No ID specified

    Given that I do not enter an ID

    When I try to complete the ToDo

    Then the todo update should have failed with the message: 'The value may not be null'

  Scenario: Empty ID

    Given that I enter the ID ''

    When I try to complete the ToDo

    Then the todo update should have failed with the message: 'The value may not be empty'

  Scenario: ToDo does not exist

    Given that I enter the ID '42'
    And that the ToDo does not exist

    When I try to complete the ToDo

    Then the todo update should have failed with the message: 'The ToDo does not exist'

  Scenario: ToDo is already done

    Given that I enter the ID '42'
    And that the following ToDo exists:
      | identity | headline | description | created             | last modified       | completed           | state |
      | 42       | Test     | Lorem ipsum | 2022-01-01T12:00:00 | 2022-01-01T13:00:00 | 2022-01-01T13:00:00 | DONE  |

    When I try to complete the ToDo

    Then the todo update should have failed with the message: 'The ToDo cannot be completed in its current state'

  Scenario: Successful update

    Given that I enter the ID '42'
    And that the following ToDo exists:
      | identity | headline | description | created             | last modified       | completed           | state |
      | 42       | Test     | Lorem ipsum | 2022-01-01T12:00:00 | 2022-01-01T13:00:00 | 2022-01-01T13:00:00 | OPEN  |

    When I try to complete the ToDo

    Then the todo update should have been successful