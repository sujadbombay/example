package com.relias.example.servie;

import com.relias.example.entity.Transaction;
import com.relias.example.exception.EntityNotFoundException;
import com.relias.example.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.util.List;

import static com.relias.example.rest.dto.ErrorCode.CUSTOMER_TRANSACTIONS_NOT_FOUND;

@Service
@Transactional
public class CustomerTransactionService {

    private TransactionRepository transactionRepository;

    public CustomerTransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Transaction saveTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    public List<Transaction> getAll() {
        return transactionRepository.findAll();
    }

    public List<Transaction> getTransactionById(String customerId) {
        List<Transaction>  transactions = transactionRepository.findByCustomerId(customerId);
        if(CollectionUtils.isEmpty(transactions)){
            throw new EntityNotFoundException(CUSTOMER_TRANSACTIONS_NOT_FOUND, customerId);
        }

        return transactions;
    }

    public void deleteTransactionById(String customerId) {
        transactionRepository.deleteByCustomerId(customerId);
    }
}
