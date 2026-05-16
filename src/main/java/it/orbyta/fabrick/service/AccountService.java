package it.orbyta.fabrick.service;

import it.orbyta.fabrick.client.FabrickClient;
import it.orbyta.fabrick.dto.request.moneyTransfer.MoneyTransferRequest;
import it.orbyta.fabrick.dto.response.BalanceResponse;
import it.orbyta.fabrick.dto.response.moneyTransfer.MoneyTransferResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AccountService {

    @Autowired
    private FabrickClient fabrickClient;


    public BalanceResponse getBalance(Long accountId) {
        return fabrickClient.getBalance(accountId);
    }

    public MoneyTransferResponse createMoneyTransfer(Long accountId, MoneyTransferRequest request) {
        return fabrickClient.createMoneyTransfer(accountId, request);
    }
}
