Feature: Fetch todo list

  As a user
  I should be able to fetch a list of todos
  In order to get an overview of the stored todos.

  Scenario Outline: No ToDos exist - <backend>

    Given that I configure the application to use the '<backend>' todo gateway

    Given that no ToDos exist

    When I try to fetch the ToDo list

    Then the ToDo list should be empty

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

  Scenario Outline: Some ToDos exist - <backend>

    Given that I configure the application to use the '<backend>' todo gateway

    Given that the following ToDo exists:
      | identity | headline | description | created             | last modified       | state |
      | 42       | Test     | Lorem ipsum | 2022-01-01T12:00:00 | 2022-01-01T13:00:00 | OPEN  |

    When I try to fetch the ToDo list

    Then the ToDo list should contain the following ToDos
      | identity | headline | description | created             | last modified       | state |
      | 42       | Test     | Lorem ipsum | 2022-01-01T12:00:00 | 2022-01-01T13:00:00 | OPEN  |

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