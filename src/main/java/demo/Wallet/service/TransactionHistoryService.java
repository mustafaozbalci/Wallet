package demo.Wallet.service;

import demo.Wallet.entity.TransactionHistory;
import demo.Wallet.repository.TransactionHistoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TransactionHistoryService {
    private static final Logger logger = LoggerFactory.getLogger(TransactionHistoryService.class);

    private final TransactionHistoryRepository transactionHistoryRepository;

    public TransactionHistoryService(TransactionHistoryRepository transactionHistoryRepository) {
        this.transactionHistoryRepository = transactionHistoryRepository;
    }

    public void createTransaction(Long walletIdFrom, Long walletIdTo, Double amount) {
        logger.info("Creating transaction from walletId: {} to walletId: {} with amount: {}", walletIdFrom, walletIdTo, amount);

        TransactionHistory transactionHistory = new TransactionHistory(walletIdFrom, walletIdTo, amount, LocalDateTime.now());

        transactionHistoryRepository.save(transactionHistory);

        logger.info("Transaction created successfully");
    }
}
