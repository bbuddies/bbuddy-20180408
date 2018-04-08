package com.odde.bbuddy.budget;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BudgetController {

    @GetMapping("/budgets/add")
    public String addBudget() {
        return "/budgets/add";
    }
}
