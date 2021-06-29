package com.relias.example.rest;

import com.relias.example.entity.Transaction;
import com.relias.example.rest.dto.APIResponseData;
import com.relias.example.rest.dto.APIResponseDataList;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@Slf4j
class ExampleController {

    private final AtomicInteger testGauge;
    private final Counter testCounter;

    private final com.relias.example.servie.CustomerTransactionService transactionService;

    private ExampleController(com.relias.example.servie.CustomerTransactionService transactionService,
                              MeterRegistry meterRegistry) {
        this.testGauge = meterRegistry.gauge("create_transaction", new AtomicInteger(0));
        this.testCounter = meterRegistry.counter("custom_counter");
        this.transactionService = transactionService;
    }

    @PostMapping("/transaction")
    private ResponseEntity<APIResponseData<Transaction>> save(@RequestBody Transaction transaction) {
        log.debug("Request for transaction received {}", transaction);
        Transaction savedTransaction = transactionService.saveTransaction(transaction);

        testGauge.set(savedTransaction.getId().intValue());
        testCounter.increment();

        APIResponseData<Transaction> response = new APIResponseData<>();
        response.setSuccess(true);
        response.setData(savedTransaction);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/transaction")
    private List<Transaction> all() {
        return transactionService.getAll();
    }


    @GetMapping("/transaction/{customerId}")
    private ResponseEntity<APIResponseDataList<Transaction>> getTransactionById(@PathVariable String customerId) {
        log.debug("Request transaction received with parameter customer Id {}", customerId);
        List<Transaction> transactions = transactionService.getTransactionById(customerId);
        APIResponseDataList<Transaction> response = new APIResponseDataList<>();
        response.setSuccess(true);
        response.setData(transactions);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/transaction/{customerId}")
    private void deleteTransactionById(@PathVariable String customerId) {
        log.debug("Delete request transaction received with parameter customer Id {}", customerId);
        transactionService.deleteTransactionById(customerId);
    }
}