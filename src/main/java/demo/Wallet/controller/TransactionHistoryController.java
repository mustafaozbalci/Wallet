package demo.Wallet.controller;

import demo.Wallet.dto.ResponseModel;
import demo.Wallet.entity.TransactionHistory;
import demo.Wallet.repository.TransactionHistoryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionHistoryController {
    private final TransactionHistoryRepository transactionHistoryRepository;

    public TransactionHistoryController(TransactionHistoryRepository transactionHistoryRepository) {
        this.transactionHistoryRepository = transactionHistoryRepository;
    }

    @GetMapping
    public ResponseEntity<ResponseModel> getAllTransactions() {
        List<TransactionHistory> transactions = transactionHistoryRepository.findAll();
        ResponseModel responseModel = new ResponseModel(HttpStatus.OK.value(), "Transactions retrieved successfully", transactions);
        return new ResponseEntity<>(responseModel, HttpStatus.OK);
    }
}
