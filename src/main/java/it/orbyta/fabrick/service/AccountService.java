package it.orbyta.fabrick.service;

import it.orbyta.fabrick.client.FabrickClient;
import it.orbyta.fabrick.dto.request.moneyTransfer.MoneyTransferRequest;
import it.orbyta.fabrick.dto.response.BalanceResponse;
import it.orbyta.fabrick.dto.response.FabrickResponse;
import it.orbyta.fabrick.dto.response.moneyTransfer.MoneyTransferResponse;
import it.orbyta.fabrick.dto.response.transactions.Transaction;
import it.orbyta.fabrick.dto.response.transactions.TransactionsResponse;
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

    public FabrickResponse<BalanceResponse> getBalance(String accountId) {
        return fabrickClient.getBalance(accountId);
    }

    public FabrickResponse<MoneyTransferResponse> createMoneyTransfer(String accountId, MoneyTransferRequest request) {
        return fabrickClient.createMoneyTransfer(accountId, request);
    }

    @Transactional
    public FabrickResponse<TransactionsResponse> getAndStoreTransactions(String accountId, LocalDate fromAccountingDate, LocalDate toAccountingDate) {
        FabrickResponse<TransactionsResponse> transactionResponse = fabrickClient.getTransactions(accountId, fromAccountingDate, toAccountingDate);
        saveTransactions(accountId, transactionResponse);
        return transactionResponse;
    }

    public List<TransactionEntity> getStoredTransactions(String accountId) {
        return transactionRepository.findByAccountIdOrderByAccountingDateDesc(accountId);
    }

    private void saveTransactions(String accountId, FabrickResponse<TransactionsResponse> transactionsResponse) {
        if (transactionsResponse == null || transactionsResponse.getPayload() == null) {
            return;
        }

        transactionsResponse.getPayload().getList().stream()
                .filter(transaction -> transaction.getTransactionId() != null)
                .filter(transaction -> !transactionRepository.existsByAccountIdAndTransactionId(accountId, transaction.getTransactionId()))
                .forEach(transaction -> {
                    TransactionEntity entity = buildEntity(accountId, transaction);
                    transactionRepository.save(entity);
                });
    }

    private TransactionEntity buildEntity(String accountId, Transaction transaction) {
        TransactionEntity entity = new TransactionEntity();
        entity.setAccountId(accountId);
        entity.setTransactionId(transaction.getTransactionId());
        entity.setOperationId(transaction.getOperationId());
        entity.setAccountingDate(transaction.getAccountingDate());
        entity.setValueDate(transaction.getValueDate());
        entity.setAmount(transaction.getAmount());
        entity.setCurrency(transaction.getCurrency());
        entity.setDescription(transaction.getDescription());

        if (transaction.getTransactionInfo() != null) {
            entity.setTypeEnumeration(transaction.getTransactionInfo().getEnumeration());
            entity.setTypeValue(transaction.getTransactionInfo().getValue());
        }
        return entity;
    }

}
