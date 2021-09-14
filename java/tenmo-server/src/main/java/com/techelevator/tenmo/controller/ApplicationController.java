package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.exceptions.InsufficientFundsException;
import org.springframework.security.access.prepost.PreAuthorize;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.security.Principal;

import java.util.List;

@RestController
@RequestMapping("/tenmo")
@PreAuthorize("isAuthenticated()")
public class ApplicationController {

    private AccountDao accountDao;
    private TransferDao transferDao;
    private UserDao userDao;


    public ApplicationController(AccountDao accountDao, TransferDao transferDao, UserDao userDao) {
        this.accountDao = accountDao;
        this.transferDao = transferDao;
        this.userDao = userDao;
    }

    @RequestMapping(path = "/users", method = RequestMethod.GET)
    public List<User> listUser() {
        return userDao.findAll();
    }


    @RequestMapping(path = "/transfers", method = RequestMethod.GET)
    public List<Transfer> listTransfer() {
        return transferDao.getAll();
    }

    @RequestMapping(path = "/accounts/byUser", method = RequestMethod.POST)
    public Account getAccountByUserId (@RequestBody User user){ //throws exception//
     int id = Math.toIntExact((Long)user.getId());
     return accountDao.getByUser(id);
    }


    @RequestMapping(path = "/transfers/byUser", method = RequestMethod.GET)
    public List<Transfer> listTransferByUser(Principal principal) { //throw exception
        int id = userDao.findIdByUsername(principal.getName());
        Account account = accountDao.getByUser(id);
        return transferDao.listByAccountId(account.getAccount_id());
    }

    @RequestMapping(path = "/transfers", method = RequestMethod.POST)
    public void createTransferByUser(Principal principal, @RequestBody Transfer transfer) throws InsufficientFundsException {
        int id = userDao.findIdByUsername(principal.getName());
        Account account = accountDao.getByUser(id);
        transfer.setAccountFrom(account.getAccount_id());

        BigDecimal trialBalance = account.getBalance().subtract(transfer.getAmount());

        if (trialBalance.compareTo(BigDecimal.ZERO) == -1) {
            throw new InsufficientFundsException();
        }

            Account accountFrom = accountDao.getByAccount((long) transfer.getAccountFrom());
            accountFrom.setBalance(accountFrom.getBalance().subtract(transfer.getAmount()));
            accountDao.updateBalance(accountFrom);

            Account accountTo = accountDao.getByAccount((long) transfer.getAccountTo());
            accountTo.setBalance(accountTo.getBalance().add(transfer.getAmount()));
            accountDao.updateBalance(accountTo);

            transferDao.createTransfer(transfer);


    }

    @RequestMapping(path = "/accounts/myAccount", method = RequestMethod.GET)
    public Account getAccountByUser(Principal principal) {
        int id = userDao.findIdByUsername(principal.getName());
        return accountDao.getByUser(id);
    }

    @RequestMapping(path = "/transfers/{id}", method = RequestMethod.GET)
    public Transfer getTransfer(@PathVariable int id) {
        return transferDao.getByTransferId(id);
    }

    @RequestMapping(path = "/accounts", method = RequestMethod.GET)
    public List<Account> listAccounts (Principal principal) //throws exception//
    {
        int id = userDao.findIdByUsername(principal.getName());
        return accountDao.listAccounts(); }

    @RequestMapping(path = "/accounts", method = RequestMethod.PUT)
    public void updateAccount (@RequestBody Account account) { //throws exception//
        accountDao.updateBalance(account);
    }

    @RequestMapping(path = "/accounts/byId", method = RequestMethod.PUT)
    public void updateAccount (@RequestBody int id) { //throws exception//
        accountDao.getBalanceByUser(id);
    }

}
