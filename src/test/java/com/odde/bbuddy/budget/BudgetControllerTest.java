package com.odde.bbuddy.budget;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.ui.Model;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

public class BudgetControllerTest {

    BudgetRepo mockBudgetRepo = mock(BudgetRepo.class);
    BudgetController controller = new BudgetController(mockBudgetRepo);
    Budget budget = new Budget();
    Model mockModel = mock(Model.class);

    @Test
    public void submit_add_budget_should_go_to_list() {
        assertThat(controller.submitAddBudget(budget, mockModel)).isEqualTo("/budgets/index");
    }
    
    @Test
    public void submit_add_budget_should_call_repo_to_save() {
        controller.submitAddBudget(budget, mockModel);

        verify(mockBudgetRepo).save(budget);
    }
    
    @Test
    public void submit_add_budget_should_set_budget_list_for_view() {
        Budget budget = new Budget();
        when(mockBudgetRepo.findAll()).thenReturn(Arrays.asList(budget));

        controller.submitAddBudget(this.budget, mockModel);

        ArgumentCaptor<List> captor = forClass(List.class);
        verify(mockModel).addAttribute(eq("budgets"), captor.capture());
        assertThat(captor.getValue()).containsExactly(budget);
    }

}