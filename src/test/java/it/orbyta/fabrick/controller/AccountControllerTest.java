package it.orbyta.fabrick.controller;

import it.orbyta.fabrick.dto.response.BalanceResponse;
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

        BalanceResponse response = new BalanceResponse();
        response.setBalance(BigDecimal.valueOf(100));

        Mockito.when(accountService.getBalance(accountId))
                .thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/accounts/" + accountId + "/balance"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balance").value(100));
    }
}
