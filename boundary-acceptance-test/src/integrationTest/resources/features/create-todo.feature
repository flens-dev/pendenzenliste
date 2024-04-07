Feature: Create ToDo

  As a user
  I should be able to create new ToDos
  In order to track my very important tasks.

  Scenario Outline: No headline - <backend>

    Given that I configure the application to use the '<backend>' todo gateway

    Given that I do not enter a headline

    When I try to create the todo

    Then creating the todo should have failed with the message 'The value may not be null'

    @eclipse-store
    Examples:
      | backend       |
      | eclipse-store |

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
      | backend          |
      | postgresql:16.2  |
      | postgresql:15.6  |
      | postgresql:14.11 |
      | postgresql:13.14 |

  Scenario Outline: No description - <backend>

    Given that I configure the application to use the '<backend>' todo gateway

    Given that I enter the headline 'Do the thing'
    And that I do not enter a description

    When I try to create the todo

    Then creating the todo should have failed with the message 'The value may not be null'

    @eclipse-store
    Examples:
      | backend       |
      | eclipse-store |

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
      | backend          |
      | postgresql:16.2  |
      | postgresql:15.6  |
      | postgresql:14.11 |
      | postgresql:13.14 |

  Scenario Outline: Successful creation - <backend>
    Given that I configure the application to use the '<backend>' todo gateway

    Given that I enter the headline 'Do the thing'
    And that I enter the description 'Yeah, I should really do the thing'

    When I try to create the todo

    Then creating the todo should have succeeded

    @eclipse-store
    Examples:
      | backend       |
      | eclipse-store |

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
      | backend          |
      | postgresql:16.2  |
      | postgresql:15.6  |
      | postgresql:14.11 |
      | postgresql:13.14 |