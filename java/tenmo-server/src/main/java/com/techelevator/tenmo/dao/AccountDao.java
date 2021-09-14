package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;

import java.math.BigDecimal;
import java.util.List;

public interface AccountDao {

    Account getByAccount(Long Id);
    Account getByUser(int Id);
    void updateBalance(Account account);
    BigDecimal getBalanceByUser(int id);
    public List<Account> listAccounts();


}
