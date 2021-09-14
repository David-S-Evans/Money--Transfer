package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class Transfer {

    int transferId;
    int transferTypeId;
    int transferStatusId;
    int accountFrom;
    int accountTo;
    BigDecimal amount;

    public Transfer(int transferId, int transferTypeId, int transferStatusId, int accountFrom, int accountTo, BigDecimal amount) {
        this.transferId = transferId;
        this.transferTypeId = transferTypeId;
        this.transferStatusId = transferStatusId;
        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
        this.amount = amount;
    }

    public Transfer(){}

    @Override
    public String toString() {
        return
                ( transferId + "          " + accountFrom +
                        "              " + accountTo + "                " +
                amount
                );
    }


    public Transfer(int accountFrom, int accountTo, BigDecimal amount) {
        this.transferTypeId = 2;
        this.transferStatusId = 2;
        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
        this.amount = amount;
    }





    public int getTransferId() {
        return transferId;
    }

    public int getTransferTypeId() {
        return transferTypeId;
    }

    public int getTransferStatusId() {
        return transferStatusId;
    }

    public int getAccountFrom() {
        return accountFrom;
    }

    public int getAccountTo() {
        return accountTo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

}


