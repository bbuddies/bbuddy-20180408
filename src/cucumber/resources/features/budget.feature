@user
Feature: Budget

  Scenario: add a new budget
    When add a budget with month "2018-04" and amount 1000
    Then the following budget will exist
      | month   | amount |
      | 2018-04 | 1000   |

  Scenario: update an existing budget
    Given exists the following budgets
       | month   | amount |
       | 2018-04 | 1000   |
    When add a budget with month "2018-04" and amount 2500
    Then the following budget will exist
       | month   | amount |
       | 2018-04 | 2500   |
