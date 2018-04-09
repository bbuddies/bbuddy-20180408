package com.odde.bbuddy.budget;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.odde.bbuddy.common.Formats.DAY;
import static com.odde.bbuddy.common.Formats.parseDay;
import static com.odde.bbuddy.common.Formats.parseMonth;
import static com.odde.bbuddy.common.controller.Urls.ADD;
import static com.odde.bbuddy.common.controller.Urls.BUDGETS;
import static com.odde.bbuddy.common.controller.Urls.STAT;

@RequestMapping(BUDGETS)
@Controller
public class BudgetController {

    private final BudgetRepo repo;

    public BudgetController(BudgetRepo repo) {
        this.repo = repo;
    }

    @GetMapping(ADD)
    public String addBudget() {
        return "/budgets/add";
    }

    public String getNowString(){
         SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SS");
         return format.format(new Date());
    }
    @PostMapping(ADD)
    public String submitAddBudget(@Valid @ModelAttribute Budget budget, Model model) {
        List<Budget> lst = repo.findBudgetByMonthEquals(budget.getMonth());
        Budget old = lst != null && lst.size()>0?lst.get(0):null;

        if(old != null){
            budget.setId(old.getId());
        }
        repo.save(budget);

        model.addAttribute("budgets", repo.findAll());
        return "/budgets/index";
    }
    @GetMapping
    public String index(Model model) {

        model.addAttribute("budgets", repo.findAll());
        return "/budgets/index";
    }

    @GetMapping(STAT)
    public String statGet(Model model) {

        model.addAttribute("budgets", repo.findAll());
        return "/budgets/stat";
    }

    @PostMapping(STAT)
    public String statPost(String startDate,String endDate, Model model) {
        LocalDate dtStart =parseDay(startDate);
        LocalDate dtEnd = parseDay(endDate);

        List<Budget> all = repo.findAll();
        List<Budget> matchList = new ArrayList<Budget>();
        int total = 0;
        for (Budget budget:all) {
            int amount = stat(dtStart,dtEnd,budget);
            if(amount > 0){
                total += amount;
                matchList.add(budget);
            }
        }

        model.addAttribute("total", total);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("budgets", matchList);
        return "/budgets/stat";
    }

    private int stat(LocalDate dtStart,LocalDate dtEnd,Budget budget){

        LocalDate xStart = budget.getMonth();
        LocalDate xEnd = budget.getMonth().plusMonths(1).minusDays(1);
        int days = budget.getMonth().lengthOfMonth();

        LocalDate hStart = xStart.isAfter(dtStart)?xStart:dtStart;
        LocalDate hEnd = xEnd.isBefore(dtEnd)?xEnd:dtEnd;

        if(hStart.isAfter(hEnd)){
            return  0;
        }

        Period p = Period.between(hStart,hEnd);

        return  p.getDays()<0 ? 0 : budget.getAmount() * (p.getDays()+1)/days;
    }
}
