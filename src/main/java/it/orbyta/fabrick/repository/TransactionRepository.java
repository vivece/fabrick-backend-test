package it.orbyta.fabrick.repository;

import it.orbyta.fabrick.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, String> {

    List<TransactionEntity> findByAccountIdOrderByAccountingDateDesc(String accountId);

    boolean existsByAccountIdAndTransactionId(String accountId, String transactionId);

}
