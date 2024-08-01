package demo.Wallet.service;


import demo.Wallet.dto.ResponseModel;
import demo.Wallet.entity.TransactionHistory;
import demo.Wallet.entity.User;
import demo.Wallet.entity.Wallet;
import demo.Wallet.exception.BadRequestException;
import demo.Wallet.exception.ErrorCodes;
import demo.Wallet.repository.TransactionHistoryRepository;
import demo.Wallet.repository.UserRepository;
import demo.Wallet.repository.WalletRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class WalletService {
    private static final Logger logger = LoggerFactory.getLogger(WalletService.class);

    private final UserRepository userRepository;
    private final WalletRepository walletRepository;
    private final TransactionHistoryRepository transactionHistoryRepository;

    public WalletService(UserRepository userRepository, WalletRepository walletRepository, TransactionHistoryRepository transactionHistoryRepository) {
        this.userRepository = userRepository;
        this.walletRepository = walletRepository;
        this.transactionHistoryRepository = transactionHistoryRepository;
    }

    @Transactional
    public ResponseEntity<ResponseModel> loadMoney(String username, String password, Double amount) {
        logger.info("Load money request initiated for user: {}", username);
        User user = userRepository.findByUsernameAndPassword(username, password).orElseThrow(() -> new BadRequestException("Invalid username or password", ErrorCodes.INVALID_USERNAME_OR_PASSWORD));
        Wallet wallet = walletRepository.findByUser(user).orElseThrow(() -> new BadRequestException("Wallet not found", ErrorCodes.WALLET_NOT_FOUND));
        wallet.setBalance(wallet.getBalance() + amount);
        walletRepository.save(wallet);
        logger.info("Money loaded successfully for user: {}", username);

        ResponseModel responseModel = new ResponseModel(HttpStatus.OK.value(), "Money loaded successfully", wallet);
        return new ResponseEntity<>(responseModel, HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<ResponseModel> makePayment(String payerUsername, String payerPassword, Long payeeUserId, Double amount) {
        logger.info("Payment request initiated from user: {} to userId: {}", payerUsername, payeeUserId);
        User payer = userRepository.findByUsernameAndPassword(payerUsername, payerPassword).orElseThrow(() -> new BadRequestException("Invalid username or password", ErrorCodes.INVALID_USERNAME_OR_PASSWORD));
        Wallet payerWallet = walletRepository.findByUser(payer).orElseThrow(() -> new BadRequestException("Payer wallet not found", ErrorCodes.WALLET_NOT_FOUND));

        if (payerWallet.getBalance() < amount) {
            logger.warn("Insufficient balance for user: {}", payerUsername);
            throw new BadRequestException("Insufficient balance", ErrorCodes.INSUFFICIENT_BALANCE);
        }

        User payee = userRepository.findById(payeeUserId).orElseThrow(() -> new BadRequestException("Payee not found", ErrorCodes.PAYEE_NOT_FOUND));
        Wallet payeeWallet = walletRepository.findByUser(payee).orElseThrow(() -> new BadRequestException("Payee wallet not found", ErrorCodes.PAYEE_NOT_FOUND));

        // Ödeme yapandan miktarı azalt
        payerWallet.setBalance(payerWallet.getBalance() - amount);
        walletRepository.save(payerWallet);

        // Alıcının miktarı artır
        payeeWallet.setBalance(payeeWallet.getBalance() + amount);
        walletRepository.save(payeeWallet);

        logger.info("Payment of {} from user: {} to userId: {} was successful", amount, payerUsername, payeeUserId);
        TransactionHistory transactionHistory = new TransactionHistory();
        transactionHistory.setWalletIdFrom(payerWallet.getId());
        transactionHistory.setWalletIdTo(payeeWallet.getId());
        transactionHistory.setAmount(amount);
        transactionHistory.setTransactionTime(LocalDateTime.now());
        transactionHistoryRepository.save(transactionHistory);

        ResponseModel responseModel = new ResponseModel(HttpStatus.OK.value(), "Payment made successfully", null);
        return new ResponseEntity<>(responseModel, HttpStatus.OK);
    }
}
