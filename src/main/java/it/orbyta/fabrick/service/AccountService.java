package it.orbyta.fabrick.service;

import it.orbyta.fabrick.client.FabrickClient;
import it.orbyta.fabrick.dto.enumerations.MoneyTransferStatusType;
import it.orbyta.fabrick.dto.request.moneyTransfer.MoneyTransferRequest;
import it.orbyta.fabrick.dto.response.BalanceResponse;
import it.orbyta.fabrick.dto.response.FabrickResponse;
import it.orbyta.fabrick.dto.response.moneyTransfer.MoneyTransferResponse;
import it.orbyta.fabrick.dto.response.transactions.Transaction;
import it.orbyta.fabrick.dto.response.transactions.TransactionsResponse;
import it.orbyta.fabrick.entity.TransactionEntity;
import it.orbyta.fabrick.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.ResourceAccessException;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountService {

    private final FabrickClient fabrickClient;
    private final TransactionRepository transactionRepository;

    public FabrickResponse<BalanceResponse> getBalance(String accountId) {
        return fabrickClient.getBalance(accountId);
    }

    public FabrickResponse<MoneyTransferResponse> createMoneyTransfer(String accountId, MoneyTransferRequest request) {
        try {
            return fabrickClient.createMoneyTransfer(accountId, request);
        } catch (ResourceAccessException ex) {
             return validationEnquiry(accountId, request, ex);
        }
    }

    private FabrickResponse<MoneyTransferResponse> validationEnquiry(String accountId, MoneyTransferRequest request, ResourceAccessException ex) {
        log.warn("Performing validation enquiry for accountId={}", accountId);
        LocalDate today = request.getExecutionDate();
        FabrickResponse<TransactionsResponse> transactions = fabrickClient.getTransactions(accountId, today, today);
        if (transactions != null && transactions.getPayload() != null) {
            boolean found = transactions.getPayload().getList().stream().anyMatch(tx -> matchesTransfer(tx, request));
            if (found) {
                log.warn("Validation enquiry found a matching transaction: money transfer was already executed. accountId={}", accountId);
                FabrickResponse<MoneyTransferResponse> alreadyExecuted = new FabrickResponse<>();
                alreadyExecuted.setStatus(MoneyTransferStatusType.EXECUTED.name());
                return alreadyExecuted;
            }
        }
        throw ex;
    }

    private boolean matchesTransfer(Transaction tx, MoneyTransferRequest request) {
        boolean amountMatch = tx.getAmount() != null && request.getAmount() != null && tx.getAmount().abs().compareTo(request.getAmount()) == 0;
        boolean currencyMatch = request.getCurrency() != null && request.getCurrency().equals(tx.getCurrency());
        boolean descriptionMatch = tx.getDescription() != null && request.getDescription() != null && tx.getDescription().equals(request.getDescription());
        return amountMatch && currencyMatch && descriptionMatch;
    }

    public FabrickResponse<TransactionsResponse> getAndStoreTransactions(String accountId, LocalDate fromAccountingDate, LocalDate toAccountingDate) {
        FabrickResponse<TransactionsResponse> transactionResponse = fabrickClient.getTransactions(accountId, fromAccountingDate, toAccountingDate);
        saveTransactions(accountId, transactionResponse);
        return transactionResponse;
    }

    public List<TransactionEntity> getStoredTransactions(String accountId) {
        return transactionRepository.findByAccountIdOrderByAccountingDateDesc(accountId);
    }

    @Transactional
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
