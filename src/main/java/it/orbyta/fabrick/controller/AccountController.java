package it.orbyta.fabrick.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import it.orbyta.fabrick.dto.response.BalanceResponse;
import it.orbyta.fabrick.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zalando.problem.Problem;

import javax.validation.constraints.NotNull;

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
