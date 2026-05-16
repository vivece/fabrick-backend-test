package it.orbyta.fabrick.service;

import it.orbyta.fabrick.client.FabrickClient;
import it.orbyta.fabrick.dto.request.MoneyTransferRequest;
import it.orbyta.fabrick.dto.response.*;
import it.orbyta.fabrick.entity.TransactionEntity;
import it.orbyta.fabrick.repository.TransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
public class AccountService {

    @Autowired
    private FabrickClient fabrickClient;

    @Autowired
    private TransactionRepository transactionRepository;

    public BalanceResponse getBalance(Long accountId) {
        return fabrickClient.getBalance(accountId);
    }

}
