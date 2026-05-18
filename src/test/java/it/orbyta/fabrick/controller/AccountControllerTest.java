package it.orbyta.fabrick.controller;

import it.orbyta.fabrick.dto.response.BalanceResponse;
import it.orbyta.fabrick.dto.response.FabrickResponse;
import it.orbyta.fabrick.service.AccountService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AccountController.class)
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    @Test
    void getBalance_shouldReturn200() throws Exception {
        String accountId = "1";

        BalanceResponse balanceResponse = new BalanceResponse();
        balanceResponse.setBalance(BigDecimal.valueOf(100));

        FabrickResponse<BalanceResponse> response = new FabrickResponse<>();
        response.setStatus("OK");
        response.setPayload(balanceResponse);

        Mockito.when(accountService.getBalance(accountId)).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/accounts/" + accountId + "/balance"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.payload.balance").value(100));

        Mockito.verify(accountService, Mockito.times(1)).getBalance(accountId);
    }

    private String validMoneyTransferJson() {
        return "{"
                + "\"creditor\":{"
                + "\"name\":\"Mario Rossi\","
                + "\"account\":{\"accountCode\":\"IT23A0336844430152923804660\"}"
                + "},"
                + "\"description\":\"Bonifico test\","
                + "\"currency\":\"EUR\","
                + "\"amount\":1.00,"
                + "\"executionDate\":\"2019-04-01\""
                + "}";
    }
}
