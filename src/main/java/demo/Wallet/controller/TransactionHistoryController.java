package demo.Wallet.controller;

import demo.Wallet.dto.ResponseModel;
import demo.Wallet.entity.TransactionHistory;
import demo.Wallet.repository.TransactionHistoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionHistoryController {
    private static final Logger logger = LoggerFactory.getLogger(TransactionHistoryController.class);

    private final TransactionHistoryRepository transactionHistoryRepository;

    public TransactionHistoryController(TransactionHistoryRepository transactionHistoryRepository) {
        this.transactionHistoryRepository = transactionHistoryRepository;
    }

    @GetMapping
    public ResponseEntity<ResponseModel<List<TransactionHistory>>> getAllTransactions() {
        logger.info("Get all transactions request received");
        List<TransactionHistory> transactions = transactionHistoryRepository.findAll();
        ResponseModel<List<TransactionHistory>> responseModel = new ResponseModel<>(HttpStatus.OK.value(), "Transactions retrieved successfully", transactions);
        logger.info("Get all transactions response: {}", responseModel.getMessage());
        return new ResponseEntity<>(responseModel, HttpStatus.OK);
    }
}
