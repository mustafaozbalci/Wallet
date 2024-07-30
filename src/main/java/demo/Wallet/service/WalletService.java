package demo.Wallet.service;


import demo.Wallet.dto.ResponseModel;
import demo.Wallet.entity.TransactionHistory;
import demo.Wallet.entity.User;
import demo.Wallet.entity.Wallet;
import demo.Wallet.exception.InsufficientBalanceException;
import demo.Wallet.exception.UserNotFoundException;
import demo.Wallet.exception.WalletNotFoundException;
import demo.Wallet.repository.TransactionHistoryRepository;
import demo.Wallet.repository.UserRepository;
import demo.Wallet.repository.WalletRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class WalletService {
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
        User user = userRepository.findByUsernameAndPassword(username, password)
                .orElseThrow(() -> new RuntimeException("Invalid username or password"));
        Wallet wallet = walletRepository.findByUser(user)
                .orElseThrow(() -> new WalletNotFoundException("Wallet not found"));
        wallet.setBalance(wallet.getBalance() + amount);
        walletRepository.save(wallet);

        ResponseModel responseModel = new ResponseModel(HttpStatus.OK.value(), "Money loaded successfully", wallet);
        return new ResponseEntity<>(responseModel, HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<ResponseModel> makePayment(String payerUsername, String payerPassword, Long payeeUserId, Double amount) {
        User payer = userRepository.findByUsernameAndPassword(payerUsername, payerPassword)
                .orElseThrow(() -> new UserNotFoundException("Invalid username or password"));
        Wallet payerWallet = walletRepository.findByUser(payer)
                .orElseThrow(() -> new WalletNotFoundException("Payer wallet not found"));

        if (payerWallet.getBalance() < amount) {
            throw new InsufficientBalanceException("Insufficient balance");
        }

        User payee = userRepository.findById(payeeUserId)
                .orElseThrow(() -> new UserNotFoundException("Payee not found"));
        Wallet payeeWallet = walletRepository.findByUser(payee)
                .orElseThrow(() -> new UserNotFoundException("Payee wallet not found"));

        // Ödeme yapandan miktarı azalt
        payerWallet.setBalance(payerWallet.getBalance() - amount);
        walletRepository.save(payerWallet);

        // Alıcının miktarı artır
        payeeWallet.setBalance(payeeWallet.getBalance() + amount);
        walletRepository.save(payeeWallet);


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