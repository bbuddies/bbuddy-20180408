package com.odde.bbuddy.acceptancetest.data.budget;
import com.odde.bbuddy.budget.Budget;
import org.springframework.context.annotation.Scope;
import org.springframework.data.repository.Repository;

import javax.transaction.Transactional;

@Transactional
@Scope("cucumber-glue")
public interface BudgetRepoForTest extends Repository<Budget, Long> {
    void save(Budget budget);
}
