Feature: Create ToDo

  As a user
  I should be able to create new ToDos
  In order to track my very important tasks.

  Scenario: No headline

    Given that I do not enter a headline

    When I try to create the todo

    Then creating the todo should have failed with the message 'The value may not be null'

  Scenario: No description

    Given that I enter the headline 'Do the thing'
    And that I do not enter a description

    When I try to create the todo

    Then creating the todo should have failed with the message 'The value may not be null'

  Scenario: Successful creation
    Given that I enter the headline 'Do the thing'
    And that I enter the description 'Yeah, I should really do the thing'

    When I try to create the todo

    Then creating the todo should have succeeded