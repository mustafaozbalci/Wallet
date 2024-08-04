package demo.Wallet.service;

import demo.Wallet.dto.ResponseModel;
import demo.Wallet.entity.User;
import demo.Wallet.entity.Wallet;
import demo.Wallet.repository.UserRepository;
import demo.Wallet.repository.WalletRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final WalletRepository walletRepository;

    public UserService(UserRepository userRepository, WalletRepository walletRepository) {
        this.userRepository = userRepository;
        this.walletRepository = walletRepository;
    }

    public ResponseEntity<ResponseModel> registerUser(User user) {
        logger.info("User registration initiated for username: {}", user.getUsername());

        User savedUser = userRepository.save(user);
        Wallet wallet = new Wallet();
        wallet.setUser(savedUser);
        wallet.setBalance(500.0);
        walletRepository.save(wallet);
        logger.info("User registered successfully with username: {}", user.getUsername());

        ResponseModel responseModel = new ResponseModel(HttpStatus.CREATED.value(), "User registered successfully", savedUser);
        return new ResponseEntity<>(responseModel, HttpStatus.CREATED);
    }
}
