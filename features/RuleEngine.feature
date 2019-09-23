Feature: Passing signals to rule engine

  Scenario Outline: Passing signal of type <type> that passes all rules
    Given Clear rules
    And I load rule ATL2 GREATERTHAN 5
    And I load rule ATL2 LESSTHAN 15
    And I load rule ATL1 EQUALS hello
    And I load rule ATL3 NOTFUTURE
    And I load rule ATL4 NOTEQUALS hello
    And I load rule ATL5 LESSTHANEQUALS 10
    And I load rule ATL6 FUTURE
    And I started the rule engine
    When I send a signal with name as <signal>, value as <value> and value type as <value_type>
    And Wait for 1000 milliseconds
    Then There should be 0 failed signals
    Examples:
      | type     | signal | value               | value_type |
      | string   | ATL1   | hello               | String     |
      | int      | ATL2   | 10                  | Integer    |
      | datetime | ATL3   | 20/03/2019 13:13:13 | DateTime   |
      | string   | ATL4   | locomotive          | String     |
      | int      | ATL5   | 10                  | Integer    |
      | datetime | ATL6   | 20/03/2022 13:13:13 | DateTime   |

  Scenario Outline: Passing invalid signal of type <type>
    Given Clear rules
    And I load rule ATL2 GREATERTHAN 5
    And I load rule ATL2 LESSTHAN 15
    And I load rule ATL1 EQUALS hello
    And I load rule ATL3 NOTFUTURE
    And I load rule ATL4 NOTEQUALS locomotive
    And I load rule ATL5 LESSTHAN 5
    And I load rule ATL6 FUTURE
    And I started the rule engine
    When I send a signal with name as <signal>, value as <value> and value type as <value_type>
    And Wait for 1000 milliseconds
    Then There should be a failed signal with name as <signal>, value as <value> and value type as <value_type>
    Examples:
      | type     | signal | value               | value_type |
      | string   | ATL1   | welcome             | String     |
      | int      | ATL2   | 20                  | Integer    |
      | datetime | ATL3   | 20/03/2022 13:13:13 | DateTime   |
      | string   | ATL4   | locomotive          | String     |
      | int      | ATL5   | 10                  | Integer    |
      | datetime | ATL6   | 20/03/2018 13:13:13 | DateTime   |

  Scenario: Passing signals with empty rules
    Given Clear rules
    When I send a signal with name as ATL1, value as 20 and value type as Integer
    And I started the rule engine
    And Wait for 1000 milliseconds
    Then There should be 0 failed signals

  Scenario: Pass a signal not mentioned in rules
    Given Clear rules
    And I load rule ATL2 GREATERTHAN 5
    And I load rule ATL2 LESSTHAN 15
    And I load rule ATL1 EQUALS hello
    And I load rule ATL3 NOTFUTURE
    And I load rule ATL4 NOTEQUALS locomotive
    And I load rule ATL5 LESSTHAN 5
    And I load rule ATL6 FUTURE
    And I started the rule engine
    When I send a signal with name as ATL9, value as 12 and value type as Integer
    And Wait for 1000 milliseconds
    Then There should be 0 failed signals

  Scenario: Start engine twice
    Given I started the rule engine
    When I start the rule engine again
    Then It fails with exception Rule engine is already running