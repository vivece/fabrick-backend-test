package it.orbyta.fabrick.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import it.orbyta.fabrick.dto.request.MoneyTransferRequest;
import it.orbyta.fabrick.dto.response.*;
import it.orbyta.fabrick.entity.TransactionEntity;
import it.orbyta.fabrick.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<BalanceResponse> getBalance(@PathVariable @NotNull Long accountId) {
        return ResponseEntity.ok(accountService.getBalance(accountId));
    }
}
