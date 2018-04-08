package com.odde.bbuddy.budget;

import org.springframework.data.repository.Repository;

public interface BudgetRepo extends Repository<Budget, Long> {
    void save(Budget budget);
}
