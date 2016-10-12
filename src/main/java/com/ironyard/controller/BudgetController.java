package com.ironyard.controller;

import com.ironyard.data.Budget;
import com.ironyard.services.DatabaseLineService;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by nathanielellsworth on 10/11/16.
 */
@RestController
public class BudgetController {

    private DatabaseLineService D = new DatabaseLineService();

    @RequestMapping(value = "/budget", method = RequestMethod.POST)
    public Budget save(@RequestBody Budget aBudget){
        Budget saved = null;
        try{
            D.save(aBudget);
            saved = D.getBudgetById(aBudget.getId());
        }catch (SQLException x){
            x.printStackTrace();
        }
        return saved;
    }

    @RequestMapping(value = "/budget/update", method = RequestMethod.POST)
    public Budget update(@RequestBody Budget aBudget){
        Budget updated = null;
        try{
            D.update(aBudget);
            updated = D.getBudgetById(aBudget.getId());
        }catch(SQLException x){
            x.printStackTrace();
        }
        return updated;
    }

    @RequestMapping(value = "/budget/{id}", method = RequestMethod.GET)
    public Budget show(@PathVariable Integer id){
        Budget found =null;
        try{
            found = D.getBudgetById(id);
        }catch (SQLException x){
            x.printStackTrace();
        }
        return found;
    }

    @RequestMapping(value = "/budget", method = RequestMethod.GET)
    public List<Budget> list(){
        List all = null;
        try{
            all = D.getAllBudgets();
        }catch(SQLException x){
            x.printStackTrace();
        }
        return all;
    }

    @RequestMapping(value = "/budget/delete/{id}", method = RequestMethod.DELETE)
    public Budget delete(@PathVariable Integer id){
        Budget deleted = null;
            try{
                deleted = D.getBudgetById(id);
                D.delete(id);
            }catch (SQLException x){
                deleted = null;
                x.printStackTrace();
        }
        return deleted;
    }
}
