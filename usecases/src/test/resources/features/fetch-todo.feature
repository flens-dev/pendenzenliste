Feature: Fetch ToDo

  As a user
  I should be able to fetch a ToDo
  In order to retrieve the previously stored ToDos.

  Scenario: No ID specified

    Given that I do not enter an ID

    When I try to fetch the ToDo

    Then fetching the ToDo should have failed with the message: 'The value may not be null'

  Scenario: Empty ID

    Given that I enter the ID ''

    When I try to fetch the ToDo

    Then fetching the ToDo should have failed with the message: 'The value may not be empty'

  Scenario: ToDo does not exist

    Given that I enter the ID '42'
    And that the ToDo does not exist

    When I try to fetch the ToDo

    Then fetching the ToDo should have failed with the message: 'The ToDo does not exist'

  Scenario: ToDo exists

    Given that I enter the ID '42'
    And that the following ToDo exists:
      | identity | headline | description | created             | last modified       |
      | 42       | Test     | Lorem ipsum | 2022-01-01T12:00:00 | 2022-01-01T13:00:00 |

    When I try to fetch the ToDo

    Then the fetched Todo should have the following values:
      | identity | headline | description | created             | last modified       |
      | 42       | Test     | Lorem ipsum | 2022-01-01T12:00:00 | 2022-01-01T13:00:00 |