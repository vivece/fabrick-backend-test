package it.orbyta.fabrick.service;

import it.orbyta.fabrick.client.FabrickClient;
import it.orbyta.fabrick.dto.request.moneyTransfer.MoneyTransferRequest;
import it.orbyta.fabrick.dto.response.BalanceResponse;
import it.orbyta.fabrick.dto.response.FabrickResponse;
import it.orbyta.fabrick.dto.response.moneyTransfer.*;
import it.orbyta.fabrick.dto.response.transactions.TransactionsResponse;
import it.orbyta.fabrick.exception.FabrickApiException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    private FabrickClient fabrickClient;

    @InjectMocks
    private AccountService accountService;


    @Test
    void getBalance_delegatesToClient() {
        String accountId = "14537780";

        BalanceResponse payload = new BalanceResponse();
        payload.setBalance(BigDecimal.valueOf(500.00));
        payload.setCurrency("EUR");

        FabrickResponse<BalanceResponse> respnseExpected = buildFabrickResponse(payload);

        when(fabrickClient.getBalance(accountId)).thenReturn(respnseExpected);

        FabrickResponse<BalanceResponse> result = accountService.getBalance(accountId);

        assertThat(result.getStatus()).isEqualTo("OK");
        assertThat(result.getPayload().getBalance()).isEqualByComparingTo(BigDecimal.valueOf(500.00));
        assertThat(result.getPayload().getCurrency()).isEqualTo("EUR");
        verify(fabrickClient, times(1)).getBalance(accountId);
    }

    @Test
    void createMoneyTransfer_delegatesToClientAndReturnsResult() {
        MoneyTransferResponse moneyTransferResponse = new MoneyTransferResponse();
        moneyTransferResponse.setMoneyTransferId("725925156");
        Creditor creditor = new Creditor();
        creditor.setName("Mario Rossi");
        Debtor debtor = new Debtor();
        debtor.setName("Giulio Bianchi");

        Account account = new Account();
        account.setAccountCode("IT60X0542811101000000123456");

        Address address = new Address();
        address.setCity("Milano");
        address.setAddress("Via Roma 10");

        creditor.setAccount(account);
        creditor.setAddress(address);
        moneyTransferResponse.setCreditor(creditor);
        moneyTransferResponse.setDebtor(debtor);

        FabrickResponse<MoneyTransferResponse> expected = buildFabrickResponse(moneyTransferResponse);

        MoneyTransferRequest request = new MoneyTransferRequest();
        String accountId = "14537780";
        when(fabrickClient.createMoneyTransfer(accountId, request)).thenReturn(expected);

        FabrickResponse<MoneyTransferResponse> result = accountService.createMoneyTransfer(accountId, request);

        assertThat(result).isNotNull();
        assertThat(result.getStatus()).isEqualTo("OK");
        assertThat(result.getPayload()).isNotNull();
        assertThat(result.getPayload().getMoneyTransferId()).isEqualTo("725925156");
        assertThat(result.getPayload().getCreditor().getName()).isEqualTo("Mario Rossi");
        assertThat(result.getPayload().getCreditor().getAccount().getAccountCode()).isEqualTo("IT60X0542811101000000123456");
        assertThat(result.getPayload().getCreditor().getAddress().getCity()).isEqualTo("Milano");
        assertThat(result.getPayload().getCreditor().getAddress().getAddress()).isEqualTo("Via Roma 10");
        assertThat(result.getPayload().getDebtor().getName()).isEqualTo("Giulio Bianchi");
        verify(fabrickClient, times(1)).createMoneyTransfer(accountId, request);
    }

    @Test
    void getTransactions_withEmptyResult_returnsEmptyList() {
        String accountId = "14537780";
        LocalDate from = LocalDate.of(2019, 1, 1);
        LocalDate to = LocalDate.of(2019, 1, 31);

        FabrickResponse<TransactionsResponse> expected = buildFabrickResponse(new TransactionsResponse());
        when(fabrickClient.getTransactions(accountId, from, to)).thenReturn(expected);

        FabrickResponse<TransactionsResponse> result = accountService.getAndStoreTransactions(accountId, from, to);

        assertThat(result.getPayload().getList()).isEmpty();
    }

    @Test
    void getTransactions_whenClientThrowsFabrickApiException() {
        String accountId = "14537780";
        LocalDate from = LocalDate.of(2019, 1, 1);
        LocalDate to = LocalDate.of(2019, 12, 31);

        FabrickApiException ex = new FabrickApiException(HttpStatus.INTERNAL_SERVER_ERROR, "FABRICK_HTTP_500", "Server error");
        when(fabrickClient.getTransactions(accountId, from, to)).thenThrow(ex);

        FabrickApiException exceptionThrown = assertThrows(FabrickApiException.class, () -> accountService.getAndStoreTransactions(accountId, from, to));
        assertEquals("Server error", exceptionThrown.getMessage());
        assertEquals(500, exceptionThrown.getHttpStatus().value());
    }

    private <T> FabrickResponse<T> buildFabrickResponse(T payload) {
        FabrickResponse<T> response = new FabrickResponse<>();
        response.setStatus("OK");
        response.setPayload(payload);
        return response;
    }
}
