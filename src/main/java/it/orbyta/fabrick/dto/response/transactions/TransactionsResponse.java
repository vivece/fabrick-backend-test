package it.orbyta.fabrick.dto.response.transactions;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class TransactionsResponse {

    private List<Transaction> list = new ArrayList<>();

}
