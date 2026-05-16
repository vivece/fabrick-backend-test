package it.orbyta.fabrick.service;

import it.orbyta.fabrick.client.FabrickClient;
import it.orbyta.fabrick.dto.response.BalanceResponse;
import it.orbyta.fabrick.repository.TransactionRepository;
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

}
