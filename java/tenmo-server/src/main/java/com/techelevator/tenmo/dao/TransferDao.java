package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.util.List;

public interface TransferDao {

    Transfer getByTransferId(int Id);
    List<Transfer> listByAccountId(int Id);
    void createTransfer(Transfer transfer);
    Transfer updateTransfer(Transfer transfer);
    List<Transfer> getAll();


}
