package com.ironyard.services;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.ironyard.data.Budget;
import com.ironyard.data.BudgetTotals;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nathanielellsworth on 10/11/16.
 */



/**
 * Created by nathanielellsworth on 10/6/16.
 *
 * CREATE TABLE budget
 (
 bud_key_id INTEGER PRIMARY KEY NOT NULL,
 bud_description VARCHAR(255),
 bud_category VARCHAR(255),
 bud_budgeted_amount INTEGER,
 bud_actual_amount INTEGER
 );
 */

//pulling all data from posgres budget table (good)
public class DatabaseLineService {

    public List<Budget> getAllBudgets() throws SQLException {
        Budget found = null;
        List<Budget> allBudgets = new ArrayList<Budget>();
        DatabaseConnection dbaInfo = new DatabaseConnection();
        Connection conn = dbaInfo.getConnection();
        PreparedStatement stmt = conn.prepareCall("SELECT * FROM finance.budget");
        ResultSet rs = stmt.executeQuery();

        //Linking Budget Class to posgres budget table
        while (rs.next()) {
            found = new Budget();
            found.setDescription(rs.getString("bud_description"));
            found.setCategory(rs.getString("bud_category"));
            found.setBudgetAmount(rs.getDouble("bud_budgeted_amount"));
            found.setActualAmount(rs.getDouble("bud_actual_amount"));
            found.setId(rs.getLong("bud_key_id"));
            allBudgets.add(found);
        }
        return allBudgets;
    }
    //Linking the 'BudgetTotals' Class to posgres and using SQL syntax to add up the 'budget' and 'actual' amounts (good)

    public List<BudgetTotals> getBudgetTotals() throws SQLException{
        BudgetTotals found = null;
        List<BudgetTotals> allBT = new ArrayList<BudgetTotals>();
        DatabaseConnection dbaInfo = new DatabaseConnection();
        Connection conn = dbaInfo.getConnection();
        PreparedStatement stmt = conn.prepareCall("SELECT bud_category, sum(bud_budgeted_amount) AS budgetTotal, sum(bud_actual_amount) AS actualTotal FROM finance.budget GROUP BY bud_category");
        ResultSet rs = stmt.executeQuery();
        while (rs.next()){
            found = new BudgetTotals();
            found.setCategory(rs.getString("bud_category"));
            found.setBudgetTotal(rs.getDouble("budgetTotal"));
            found.setActualTotal(rs.getDouble("actualTotal"));
            allBT.add(found);
        }
        return allBT;
    }

    //this is the search function where the user can search by category or description (good)

    public List<Budget> search (String search) throws SQLException{
        List<Budget> found = new ArrayList<Budget>();
        DatabaseConnection dbaInfo = new DatabaseConnection();
        Connection conn = null;
        try {
            conn = dbaInfo.getConnection();
            search = search + "%";
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM finance.budget WHERE (bud_category ILIKE ?) OR (bud_description ILIKE ?);");
            ps.setString(1, search);
            ps.setString(2, search);
            ResultSet rs = ps.executeQuery();
            found = listResults(rs);
        }catch (Exception error) {
            error.printStackTrace();
            conn.rollback();
        }finally {
            conn.close();
        }
        return found;
    }

    //Setting the list results (good)
    private List<Budget> listResults(ResultSet rs) throws SQLException{
        List<Budget> found = new ArrayList<Budget>();
        while (rs.next()){
            Budget x = new Budget();
            x.setDescription(rs.getString("bud_description"));
            x.setCategory(rs.getString("bud_category"));
            x.setBudgetAmount(rs.getDouble("bud_budgeted_amount"));
            x.setActualAmount(rs.getDouble("bud_actual_amount"));
            x.setId(rs.getInt("bud_key_id"));
        }
        return found;
    }

    //Save Budget to Database
    public void save(Budget myBudget) throws SQLException{
        DatabaseConnection dbaInfo = new DatabaseConnection();
        Connection conn = null;
        try {
            conn = dbaInfo.getConnection();
            PreparedStatement ps = conn.prepareStatement("INSERT INTO finance.budget " +
                            "(bud_key_id, bud_description, bud_category, bud_budgeted_amount, bud_actual_amount) VALUES (nextval('finance.BUDGETS_SEQ'),?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, myBudget.getDescription());
            ps.setString(2, myBudget.getCategory());
            ps.setDouble(3, myBudget.getBudgetAmount());
            ps.setDouble(4, myBudget.getActualAmount());
            ps.executeUpdate();
        }catch(SQLException e) {
            e.printStackTrace();
            conn.rollback();
            throw e;
        }finally {
            conn.close();
        }
    }

    public Budget getBudgetById(long idCon) throws SQLException {
        DatabaseConnection dbaInfo = new DatabaseConnection();
        Connection conn = null;
        Budget foundById = null;
        try{
            conn = dbaInfo.getConnection();
            //starts with search
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM finance.budget WHERE bud_key_id = ?;");
            ps.setLong(1, idCon);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                foundById = new Budget();
                foundById.setDescription(rs.getString("bud_description"));
                foundById.setCategory(rs.getString("bud_category"));
                foundById.setBudgetAmount(rs.getDouble("bud_budgeted_amount"));
                foundById.setActualAmount(rs.getDouble("bud_actual_amount"));
                foundById.setId(rs.getLong("bud_key_id"));
            }
        }catch(SQLException x){
            x.printStackTrace();
            conn.rollback();
            throw x;
        }finally {
            conn.close();
        }
        return foundById;
    }

    public void update (Budget aBudget) throws SQLException{
        DatabaseConnection dbaInfo = new DatabaseConnection();
        Connection conn = null;
        try{
            conn = dbaInfo.getConnection();
            PreparedStatement ps = conn.prepareStatement("UPDATE finance.budget SET bud_description=?, bud_category=?, bud_budgeted_amount=?, bud_actual_amount=? WHERE bud_key_id=?;");
            ps.setString(1, aBudget.getDescription());
            ps.setString(2, aBudget.getCategory());
            ps.setDouble(3, aBudget.getBudgetAmount());
            ps.setDouble(4, aBudget.getActualAmount());
            ps.setLong(5, aBudget.getId());
            ps.executeUpdate();
        }catch(SQLException x){
            x.printStackTrace();
            conn.rollback();
            throw x;
        }finally{
            conn.close();
        }
    }

    public void delete (long id) throws SQLException{
        DatabaseConnection dbaInfo = new DatabaseConnection();
        Connection conn = null;
        try{
            conn = dbaInfo.getConnection();
            PreparedStatement ps = conn.prepareStatement("DELETE FROM finance.budget WHERE bud_key_id = ?");
            ps.setLong(1, id);
            ps.executeUpdate();
        }catch(SQLException x){
            x.printStackTrace();
            conn.rollback();
            throw x;
        }finally{
            conn.close();
        }
    }
}


