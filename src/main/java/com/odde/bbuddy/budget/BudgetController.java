package com.odde.bbuddy.budget;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class BudgetController {

    private final BudgetRepo repo;

    public BudgetController(BudgetRepo repo) {
        this.repo = repo;
    }

    @GetMapping("/budgets/add")
    public String addBudget() {
        return "/budgets/add";
    }

    @PostMapping("/budgets/add")
    public String submitAddBudget(@ModelAttribute Budget budget, Model model) {
        repo.save(budget);
        model.addAttribute("budgets", repo.findAll());
        return "/budgets/index";
    }
}
