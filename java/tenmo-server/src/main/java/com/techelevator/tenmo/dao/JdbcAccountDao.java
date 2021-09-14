package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcAccountDao implements AccountDao {

    private JdbcTemplate jdbcTemplate;


    public JdbcAccountDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Account> listAccounts() {
        ArrayList<Account> accountList = new ArrayList<>();
        Account account = null;
        String sql = "SELECT * FROM accounts;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while (results.next()) {
            account = new Account();
            account.setUser_id(results.getInt("account_id"));
            account.setUser_id(results.getInt("user_id"));
            account.setBalance((results.getBigDecimal("balance")));
            accountList.add(account);
        }
        return accountList;
    }

    @Override
    public Account getByAccount(Long Id) {
        Account account = null;
        String sql = "SELECT account_id, user_id, balance FROM accounts WHERE account_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, Id);
        if (results.next()) {
            account = new Account();
            account.setUser_id(results.getInt("account_id"));
            account.setUser_id(results.getInt("user_id"));
            account.setBalance((results.getBigDecimal("balance")));
        }
        return account;
    }

    @Override
    public Account getByUser(int Id) {
        Account account = null;
        String sql = "SELECT account_id, user_id, balance FROM accounts WHERE user_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, Id);
        if (results.next()) {
            account = new Account();
            account.setAccount_id(results.getInt("account_id"));
            account.setUser_id(results.getInt("user_id"));
            account.setBalance((results.getBigDecimal("balance")));
        }
        return account;
    }

    @Override
    public void updateBalance(Account account) {
        String sql = "UPDATE accounts SET balance = ?;";
        jdbcTemplate.update(sql, account.getBalance());

    }


    @Override
    public BigDecimal getBalanceByUser(int id) {
        BigDecimal balance = null;
        String sql = "SELECT balance FROM accounts where user_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
        if (results.next()) {
    //        String balanceString = results.getBigDecimal("balance").toString();
    //        balance = new BigDecimal(balanceString); //idk if this works lol
            balance = results.getBigDecimal("balance");
        }
        return balance;
    }
}
