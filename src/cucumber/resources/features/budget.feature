@user
Feature: Budget

  Scenario: add a new budget
    When add a budget with month "2018-04" and amount 1000
    Then the following budget will exist
      | month   | amount |
      | 2018-04 | 1000   |
