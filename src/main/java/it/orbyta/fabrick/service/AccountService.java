package it.orbyta.fabrick.service;

import it.orbyta.fabrick.client.FabrickClient;
import it.orbyta.fabrick.dto.request.moneyTransfer.MoneyTransferRequest;
import it.orbyta.fabrick.dto.response.BalanceResponse;
import it.orbyta.fabrick.dto.response.FabrickResponse;
import it.orbyta.fabrick.dto.response.moneyTransfer.MoneyTransferResponse;
import it.orbyta.fabrick.dto.response.transactions.TransactionsResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;

@Slf4j
@Service
public class AccountService {

    @Autowired
    private FabrickClient fabrickClient;


    public FabrickResponse<BalanceResponse> getBalance(String accountId) {
        return fabrickClient.getBalance(accountId);
    }

    public FabrickResponse<MoneyTransferResponse> createMoneyTransfer(String accountId, MoneyTransferRequest request) {
        return fabrickClient.createMoneyTransfer(accountId, request);
    }

    @Transactional
    public FabrickResponse<TransactionsResponse> getTransactions(String accountId, LocalDate fromAccountingDate, LocalDate toAccountingDate) {
        FabrickResponse<TransactionsResponse> response = fabrickClient.getTransactions(accountId, fromAccountingDate, toAccountingDate);
        //persistTransactions(accountId, response);
        return response;
    }

}
