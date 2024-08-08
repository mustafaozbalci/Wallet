package demo.Wallet.service;

import demo.Wallet.dto.ResponseModel;
import demo.Wallet.entity.User;
import demo.Wallet.entity.Wallet;
import demo.Wallet.exception.BadRequestException;
import demo.Wallet.exception.ErrorCodes;
import demo.Wallet.repository.WalletRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WalletService {
    private static final Logger logger = LoggerFactory.getLogger(WalletService.class);

    private final UserService userService;
    private final WalletRepository walletRepository;
    private final TransactionHistoryService transactionHistoryService;

    public void createWallet(User user) {
        logger.info("Creating wallet for user: {}", user.getUsername());
        Wallet wallet = new Wallet(user, 0.0);
        walletRepository.save(wallet);
    }

    @Transactional
    public ResponseEntity<ResponseModel<Wallet>> loadMoney(String username, String password, Double amount) {
        logger.info("Load money request initiated for user: {}", username);
        User user = userService.validateUser(username, password);

        Wallet wallet = walletRepository.findByUser(user).orElseThrow(() -> new BadRequestException("Wallet not found", ErrorCodes.WALLET_NOT_FOUND));

        wallet.setBalance(wallet.getBalance() + amount);
        walletRepository.save(wallet);
        logger.info("Money loaded successfully for user: {}", username);

        ResponseModel<Wallet> responseModel = new ResponseModel<>(HttpStatus.OK.value(), "Money loaded successfully", wallet);
        return new ResponseEntity<>(responseModel, HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<ResponseModel<Void>> makePayment(String payerUsername, String payerPassword, Long payeeWalletId, Double amount) {
        logger.info("Payment request initiated from user: {} to walletId: {}", payerUsername, payeeWalletId);

        User payer = userService.validateUser(payerUsername, payerPassword);

        Wallet payerWallet = walletRepository.findByUser(payer).orElseThrow(() -> new BadRequestException("Payer wallet not found", ErrorCodes.WALLET_NOT_FOUND));

        if (payerWallet.getBalance() < amount) {
            logger.warn("Insufficient balance for user: {}", payerUsername);
            throw new BadRequestException("Insufficient balance", ErrorCodes.INSUFFICIENT_BALANCE);
        }

        Wallet payeeWallet = walletRepository.findById(payeeWalletId).orElseThrow(() -> new BadRequestException("Payee wallet not found", ErrorCodes.PAYEE_NOT_FOUND));

        payerWallet.setBalance(payerWallet.getBalance() - amount);
        walletRepository.save(payerWallet);

        payeeWallet.setBalance(payeeWallet.getBalance() + amount);
        walletRepository.save(payeeWallet);

        logger.info("Payment of {} from user: {} to walletId: {} was successful", amount, payerUsername, payeeWalletId);

        transactionHistoryService.createTransaction(payerWallet.getId(), payeeWallet.getId(), amount);

        ResponseModel<Void> responseModel = new ResponseModel<>(HttpStatus.OK.value(), "Payment made successfully", null);
        return new ResponseEntity<>(responseModel, HttpStatus.OK);
    }
}
