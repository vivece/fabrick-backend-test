package it.orbyta.fabrick.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import it.orbyta.fabrick.dto.request.moneyTransfer.Account;
import it.orbyta.fabrick.dto.request.moneyTransfer.Creditor;
import it.orbyta.fabrick.dto.request.moneyTransfer.MoneyTransferRequest;
import it.orbyta.fabrick.dto.response.BalanceResponse;
import it.orbyta.fabrick.dto.response.FabrickResponse;
import it.orbyta.fabrick.dto.response.moneyTransfer.MoneyTransferResponse;
import it.orbyta.fabrick.dto.response.transactions.TransactionsResponse;
import it.orbyta.fabrick.entity.TransactionEntity;
import it.orbyta.fabrick.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AccountController.class)
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @Test
    void getBalance_shouldReturn200_andCallService() throws Exception {
        FabrickResponse<BalanceResponse> response = new FabrickResponse<>();
        when(accountService.getBalance("123")).thenReturn(response);

        mockMvc.perform(get("/api/accounts/123/balance"))
                .andExpect(status().isOk());

        verify(accountService).getBalance("123");
    }

    @Test
    void createMoneyTransfer_shouldReturnBadRequest() throws Exception {
        MoneyTransferRequest req = buildMoneyTransferRequestWithMandatoryField();
        req.setAmount(BigDecimal.valueOf(-100));

        mockMvc.perform(post("/api/accounts/123/money-transfers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(req)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("amount: amount must be greater than zero"));
    }

    @Test
    void createMoneyTransfer_shouldReturnOk() throws Exception {
        FabrickResponse<MoneyTransferResponse> response = new FabrickResponse<>();
        when(accountService.createMoneyTransfer(eq("123"), any()))
                .thenReturn(response);

        MoneyTransferRequest req = buildMoneyTransferRequestWithMandatoryField();

        mockMvc.perform(post("/api/accounts/123/money-transfers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(req)))
                .andExpect(status().isOk());

        verify(accountService).createMoneyTransfer(eq("123"), any(MoneyTransferRequest.class));
    }

    @Test
    void getTransactions_shouldReturn200_andCallService() throws Exception {
        FabrickResponse<TransactionsResponse> response = new FabrickResponse<>();
        when(accountService.getAndStoreTransactions(eq("123"), any(), any()))
                .thenReturn(response);

        mockMvc.perform(get("/api/accounts/123/transactions")
                        .param("fromAccountingDate", "2024-01-01")
                        .param("toAccountingDate", "2024-01-31"))
                .andExpect(status().isOk());

        verify(accountService).getAndStoreTransactions(
                eq("123"),
                any(LocalDate.class),
                any(LocalDate.class)
        );
    }

    @Test
    void getStoredTransactions_shouldReturn200_andCallService() throws Exception {
        String accountId = "123";
        when(accountService.getStoredTransactions(accountId))
                .thenReturn(List.of(new TransactionEntity()));

        mockMvc.perform(get("/api/accounts/{accountId}/transactions/stored", accountId))
                .andExpect(status().isOk());

        verify(accountService).getStoredTransactions(accountId);
    }

    private MoneyTransferRequest buildMoneyTransferRequestWithMandatoryField() {
        MoneyTransferRequest request = new MoneyTransferRequest();

        Creditor creditor = new Creditor();
        creditor.setName("Mario Rossi");
        Account account = new Account();
        account.setAccountCode("IT60X0542811101000000123456");
        creditor.setAccount(account);

        request.setCreditor(creditor);
        request.setExecutionDate(LocalDate.parse("2026-05-18"));
        request.setDescription("Bonifico di test");
        request.setAmount(BigDecimal.valueOf(10.0));
        request.setCurrency("EUR");
        return request;
    }
}