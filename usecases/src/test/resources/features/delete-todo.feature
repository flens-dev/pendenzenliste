Feature: Delete ToDo

  As a user
  I should be able to delete an existing todo
  So that I can remove it from my todo list without finishing the task.

  Scenario: No ID

    Given that I do not enter an ID

    When I try to delete the ToDo

    Then the todo update should have failed with the message: 'The value may not be null'

  Scenario: Empty ID

    Given that I enter the ID ''

    When I try to delete the ToDo

    Then the todo update should have failed with the message: 'The value may not be empty'

  Scenario: ToDo does not exist

    Given that I enter the ID '42'
    And that the ToDo does not exist

    When I try to delete the ToDo

    Then the todo update should have failed with the message: 'The ToDo does not exist'

  Scenario: Deleting the ToDo fails

    Given that I enter the ID '42'
    And that the following ToDo exists:
      | identity | headline | description | created             | last modified       | state |
      | 42       | Test     | Lorem ipsum | 2022-01-01T12:00:00 | 2022-01-01T13:00:00 | OPEN  |
    And that deleting the ToDo fails

    When I try to delete the ToDo

    Then the todo update should have failed with the message: 'Failed to delete the ToDo'

  Scenario: Deleting the ToDo succeeds

    Given that I enter the ID '42'
    And that the following ToDo exists:
      | identity | headline | description | created             | last modified       | state |
      | 42       | Test     | Lorem ipsum | 2022-01-01T12:00:00 | 2022-01-01T13:00:00 | OPEN  |
    And that deleting the ToDo succeeds

    When I try to delete the ToDo

    Then the todo update should have been successful