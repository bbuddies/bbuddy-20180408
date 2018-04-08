package com.odde.bbuddy.budget;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class BudgetControllerTest {

    BudgetRepo mockBudgetRepo = mock(BudgetRepo.class);
    BudgetController controller = new BudgetController(mockBudgetRepo);
    Budget budget = new Budget();

    @Test
    public void submit_add_budget_should_go_to_list() {
        assertThat(controller.submitAddBudget(budget)).isEqualTo("/budgets/index");
    }
    
    @Test
    public void submit_add_budget_should_call_repo_to_save() {
        controller.submitAddBudget(budget);

        verify(mockBudgetRepo).save(budget);
    }

}