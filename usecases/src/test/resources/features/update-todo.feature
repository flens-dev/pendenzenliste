Feature: Update ToDo

  As a user
  I should be able to update an open ToDo
  In order to update the details of the ToDo.

  Scenario: No ID supplied

    Given that I do not enter an ID

    When I try to update the ToDo

    Then the todo update should have failed with the message: 'The value may not be null'

  Scenario: ToDo does not exist

    Given that I enter the ID '42'
    And that I enter the headline 'Cool Headline'
    And that I enter the description 'Lorem ipsum dolor sit amet'
    And that the ToDo does not exist

    When I try to update the ToDo

    Then the todo update should have failed with the message: 'The ToDo does not exist'

  Scenario: ToDo has already been closed

    Given that I enter the ID '42'
    And that I enter the headline 'Cool Headline'
    And that I enter the description 'Lorem ipsum dolor sit amet'
    And that the following ToDo exists:
      | identity | headline | description | created             | last modified       | completed           | state |
      | 42       | Test     | Lorem ipsum | 2022-01-01T12:00:00 | 2022-01-01T13:00:00 | 2022-01-01T13:00:00 | DONE  |

    When I try to update the ToDo

    Then the todo update should have failed with the message: 'The ToDo cannot be updated in its current state'

  Scenario: Successful update

    Given that I enter the ID '42'
    And that I enter the headline 'Cool Headline'
    And that I enter the description 'Lorem ipsum dolor sit amet'
    And that the following ToDo exists:
      | identity | headline | description | created             | last modified       | state |
      | 42       | Test     | Lorem ipsum | 2022-01-01T12:00:00 | 2022-01-01T13:00:00 | OPEN  |

    When I try to update the ToDo

    Then the todo update should have been successful