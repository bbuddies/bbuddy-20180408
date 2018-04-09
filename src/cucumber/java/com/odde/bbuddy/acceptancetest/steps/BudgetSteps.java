package com.odde.bbuddy.acceptancetest.steps;

import com.odde.bbuddy.acceptancetest.data.budget.BudgetRepoForTest;
import com.odde.bbuddy.acceptancetest.driver.UiDriver;
import com.odde.bbuddy.budget.Budget;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class BudgetSteps {

    @Autowired
    UiDriver driver;
    @Autowired
    BudgetRepoForTest budgetRepoForTest;

    @When("^add a budget with month \"([^\"]*)\" and amount (\\d+)$")
    public void add_a_budget_with_month_and_amount(String month, int amount) throws Throwable {
        driver.navigateTo("/budgets/add");
        driver.inputTextByName(month, "month");
        driver.inputTextByName(String.valueOf(amount), "amount");
        driver.clickByText("Save");
    }

    @Then("^the following budget will exist$")
    public void the_following_budget_will_exist(List<Budget> budgets) throws Throwable {
        driver.waitForTextPresent(budgets.get(0).getMonth());
        driver.waitForTextPresent(budgets.get(0).getAmount());
    }

    @Given("^exists the following budgets$")
    public void exists_the_following_budgets(List<Budget> budgets) throws Throwable {
        for (Budget budget : budgets){
             budgetRepoForTest.save(budget);
        }
    }
}
