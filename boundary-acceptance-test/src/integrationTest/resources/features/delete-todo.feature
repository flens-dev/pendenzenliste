Feature: Delete ToDo

  As a user
  I should be able to delete an existing todo
  So that I can remove it from my todo list without finishing the task.

  Scenario Outline: No ID - <backend>

    Given that I configure the application to use the '<backend>' todo gateway

    Given that I do not enter an ID

    When I try to delete the ToDo

    Then the todo update should have failed with the message: 'The value may not be null'

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

  Scenario Outline: Empty ID - <backend>

    Given that I configure the application to use the '<backend>' todo gateway

    Given that I enter the ID ''

    When I try to delete the ToDo

    Then the todo update should have failed with the message: 'The value may not be empty'

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

  Scenario Outline: ToDo does not exist - <backend>

    Given that I configure the application to use the '<backend>' todo gateway

    Given that I enter the ID '42'
    And that the ToDo does not exist

    When I try to delete the ToDo

    Then the todo update should have failed with the message: 'The ToDo does not exist'

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

  Scenario Outline: Deleting the ToDo succeeds - <backend>

    Given that I configure the application to use the '<backend>' todo gateway

    Given that I enter the ID '42'
    And that the following ToDo exists:
      | identity | headline | description | created             | last modified       | state |
      | 42       | Test     | Lorem ipsum | 2022-01-01T12:00:00 | 2022-01-01T13:00:00 | OPEN  |

    When I try to delete the ToDo

    Then the todo update should have been successful
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