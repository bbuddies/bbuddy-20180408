package com.odde.bbuddy.budget;

import org.springframework.data.repository.Repository;

import java.util.List;

public interface BudgetRepo extends Repository<Budget, Long> {
    void save(Budget budget);

    List<Budget> findAll();

    List<Budget> findBudgetByMonthEquals(String month);
}
