Feature: Reset ToDo

  As a user
  I should be able to reset a completed ToDo
  In order to make it open again.

  Scenario Outline: No ID specified - <backend>

    Given that I configure the application to use the '<backend>' todo gateway

    Given that I do not enter an ID

    When I try to reset the ToDo

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

    When I try to reset the ToDo

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

    When I try to reset the ToDo

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

  Scenario Outline: ToDo is open - <backend>

    Given that I configure the application to use the '<backend>' todo gateway

    Given that I enter the ID '42'
    And that the following ToDo exists:
      | identity | headline | description | created             | last modified       | state |
      | 42       | Test     | Lorem ipsum | 2022-01-01T12:00:00 | 2022-01-01T13:00:00 | OPEN  |

    When I try to reset the ToDo

    Then the todo update should have failed with the message: 'The ToDo cannot be reset in its current state'

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

  Scenario Outline: Reset ToDo - <backend>

    Given that I configure the application to use the '<backend>' todo gateway

    Given that I enter the ID '42'
    And that the following ToDo exists:
      | identity | headline | description | created             | last modified       | completed           | state     |
      | 42       | Test     | Lorem ipsum | 2022-01-01T12:00:00 | 2022-01-01T13:00:00 | 2022-01-01T13:00:00 | COMPLETED |

    When I try to reset the ToDo

    Then the todo update should have been successful

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