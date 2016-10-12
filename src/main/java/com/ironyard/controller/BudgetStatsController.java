package com.ironyard.controller;

import com.ironyard.data.BudgetTotals;
import com.ironyard.services.DatabaseLineService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by nathanielellsworth on 10/11/16.
 */
@RestController
public class BudgetStatsController {
    private DatabaseLineService dbaServ = new DatabaseLineService();

    @RequestMapping(value = "/budgetstats", method = RequestMethod.GET)
    public List<BudgetTotals> list() {
        List<BudgetTotals> stats = null;
        try {
            stats = dbaServ.getBudgetTotals();
        } catch (SQLException x) {
            x.printStackTrace();
        }
        return stats;
    }
}
