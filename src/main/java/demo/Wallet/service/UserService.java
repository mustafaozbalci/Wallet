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

import java.util.Optional;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final WalletRepository walletRepository;

    public UserService(UserRepository userRepository, WalletRepository walletRepository) {
        this.userRepository = userRepository;
        this.walletRepository = walletRepository;
    }

    public ResponseEntity<ResponseModel> registerUser(String username, String password, String email) {
        logger.info("User registration initiated for username: {}", username);

        // Check if username already exists
        Optional<User> existingUser = userRepository.findByUsername(username);
        if (existingUser.isPresent()) {
            logger.warn("User registration failed. Username already exists: {}", username);
            ResponseModel responseModel = new ResponseModel(HttpStatus.BAD_REQUEST.value(), "Username already exists", null);
            return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
        }

        // Create new user
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);

        User savedUser = userRepository.save(user);

        // Create wallet for the new user
        Wallet wallet = new Wallet();
        wallet.setUser(savedUser);
        wallet.setBalance(0.0);
        walletRepository.save(wallet);

        logger.info("User registered successfully with username: {}", username);

        ResponseModel responseModel = new ResponseModel(HttpStatus.CREATED.value(), "User registered successfully", savedUser);
        return new ResponseEntity<>(responseModel, HttpStatus.CREATED);
    }
}
