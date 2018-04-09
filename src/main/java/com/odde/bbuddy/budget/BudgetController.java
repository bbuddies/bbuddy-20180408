package com.odde.bbuddy.budget;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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

    public String getNowString(){
         SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SS");
         return format.format(new Date());
    }
    @PostMapping("/budgets/add")
    public String submitAddBudget(@ModelAttribute Budget budget, Model model) {
        List<Budget> lst = repo.findBudgetByMonthEquals(budget.getMonth());
        Budget old = lst != null && lst.size()>0?lst.get(0):null;

        if(old != null){
            budget.setId(old.getId());
        }
        repo.save(budget);

        model.addAttribute("budgets", repo.findAll());
        return "/budgets/index";
    }
    @GetMapping("/budgets/index")
    public String index(Model model) {

        model.addAttribute("budgets", repo.findAll());
        return "/budgets/index";
    }
}
