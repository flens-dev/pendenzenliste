Feature: Purge Old ToDos

  As a user
  My completed todos should be deleted by the system
  In order to protect my data.

  Scenario Outline: Deleting the ToDo succeeds - <backend>

    Given that I configure the application to use the '<backend>' todo gateway

    Given that the following ToDo exists:
      | identity | headline | description | created             | last modified       | state     | completed           |
      | 42       | Test     | Lorem ipsum | 2022-01-01T12:00:00 | 2022-01-01T13:00:00 | COMPLETED | 2022-01-01T13:00:00 |

    When I try to purge the todos

    Then purging the todos should have succeeded
    And a 'ToDoDeletedEvent' should have been published

    Given that I enter the ID '42'
    When I try to fetch the ToDo

    Then fetching the ToDo should have failed with the message: 'The ToDo does not exist'

    @redis
    Examples:
      | backend |
      | redis   |

    @inmemory
    Examples:
      | backend  |
      | inmemory |

    @filesystem
    Examples:
      | backend    |
      | filesystem |

    @postgresql
    Examples:
      | backend    |
      | postgresql |