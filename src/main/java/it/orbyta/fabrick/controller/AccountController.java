package it.orbyta.fabrick.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import it.orbyta.fabrick.dto.request.moneyTransfer.MoneyTransferRequest;
import it.orbyta.fabrick.dto.response.BalanceResponse;
import it.orbyta.fabrick.dto.response.FabrickResponse;
import it.orbyta.fabrick.dto.response.moneyTransfer.MoneyTransferResponse;
import it.orbyta.fabrick.dto.response.transactions.TransactionsResponse;
import it.orbyta.fabrick.entity.TransactionEntity;
import it.orbyta.fabrick.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.zalando.problem.Problem;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@Validated
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/{accountId}/balance")
    @ApiOperation("Read account balance")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 403, message = "Forbidden", response = Problem.class),
            @ApiResponse(code = 422, message = "Unprocessable Entity", response = Problem.class),
            @ApiResponse(code = 502, message = "Bad Gateway", response = Problem.class)
    })
    public ResponseEntity<FabrickResponse<BalanceResponse>> getBalance(@PathVariable @NotNull String accountId) {
        return ResponseEntity.ok(accountService.getBalance(accountId));
    }

    @PostMapping("/{accountId}/money-transfers")
    @ApiOperation("Create money transfer")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 403, message = "Forbidden", response = Problem.class),
            @ApiResponse(code = 422, message = "Unprocessable Entity", response = Problem.class),
            @ApiResponse(code = 502, message = "Bad Gateway", response = Problem.class)
    })
    public ResponseEntity<FabrickResponse<MoneyTransferResponse>> createMoneyTransfer(@PathVariable @NotNull String accountId,
                                                                                     @RequestBody @Valid @NotNull MoneyTransferRequest request) {
        return ResponseEntity.ok(accountService.createMoneyTransfer(accountId, request));
    }

    @GetMapping("/{accountId}/transactions")
    @ApiOperation("Read account transactions")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 403, message = "Forbidden", response = Problem.class),
            @ApiResponse(code = 422, message = "Unprocessable Entity", response = Problem.class),
            @ApiResponse(code = 502, message = "Bad Gateway", response = Problem.class)
    })
    public ResponseEntity<FabrickResponse<TransactionsResponse>> getTransactions(@PathVariable @NotNull String accountId,
                                                                                 @RequestParam @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromAccountingDate,
                                                                                 @RequestParam @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toAccountingDate) {
        return ResponseEntity.ok(accountService.getAndStoreTransactions(accountId, fromAccountingDate, toAccountingDate));
    }

    @GetMapping("/{accountId}/transactions/stored")
    @ApiOperation("Read stored account transactions")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 422, message = "Unprocessable Entity", response = Problem.class)
    })
    public ResponseEntity<List<TransactionEntity>> getStoredTransactions(@PathVariable @NotNull String accountId) {
        return ResponseEntity.ok(accountService.getStoredTransactions(accountId));
    }

}
