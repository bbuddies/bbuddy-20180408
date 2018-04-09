package com.odde.bbuddy.budget;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static com.odde.bbuddy.common.Formats.parseMonth;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.Mockito.*;

public class BudgetControllerTest {

    private static final LocalDate MONTH =parseMonth("2018-05");
    private static final int AMOUNT = 1500;
    private static final int EXISTING_AMOUNT = 1000;
    private static final int ID = 100;
    BudgetRepo mockBudgetRepo = mock(BudgetRepo.class);
    BudgetController controller = new BudgetController(mockBudgetRepo);
    Budget budget = new Budget();
    Model mockModel = mock(Model.class);

    @Test
    public void submit_add_budget_should_go_to_list() {
        assertThat(addBudget(budget)).isEqualTo("/budgets/index");
    }
    
    @Test
    public void add_a_new_budget() {
        givenExistingBudget();

        addBudget(budget(MONTH, AMOUNT));

        verifySaveBudget(0, MONTH, AMOUNT);
    }

    @Test
    public void update_an_exists_budget() {
        givenExistingBudget(budget(ID, MONTH, EXISTING_AMOUNT));

        addBudget(budget(MONTH, AMOUNT));

        verifySaveBudget(ID, MONTH, AMOUNT);
    }

    @Test
    public void submit_add_budget_should_set_budget_list_for_view() {
        Budget budget = new Budget();
        when(mockBudgetRepo.findAll()).thenReturn(Arrays.asList(budget));

        addBudget(this.budget);

        ArgumentCaptor<List> captor = forClass(List.class);
        verify(mockModel).addAttribute(eq("budgets"), captor.capture());
        assertThat(captor.getValue()).containsExactly(budget);
    }

    private String addBudget(Budget budget) {
        return controller.submitAddBudget(budget, mockModel);
    }

    private void verifySaveBudget(int id, LocalDate month, int amount) {
        ArgumentCaptor<Budget> captor = forClass(Budget.class);
        verify(mockBudgetRepo).save(captor.capture());
        assertThat(captor.getValue().getMonth()).isEqualTo(month);
        assertThat(captor.getValue().getAmount()).isEqualTo(amount);
        assertThat(captor.getValue().getId()).isEqualTo(id);
    }

    private void givenExistingBudget(Budget... budgets) {
        when(mockBudgetRepo.findBudgetByMonthEquals(any(LocalDate.class))).thenReturn(Arrays.asList(budgets));
    }

    private Budget budget(LocalDate month, int amount) {
        Budget budget = new Budget();
        budget.setMonth(month);
        budget.setAmount(amount);
        return budget;
    }

    private Budget budget(int id, LocalDate month, int amount) {
        Budget existingBudget = new Budget();
        existingBudget.setId(id);
        existingBudget.setMonth(month);
        existingBudget.setAmount(amount);
        return existingBudget;
    }

}