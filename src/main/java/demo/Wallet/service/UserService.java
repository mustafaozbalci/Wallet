package demo.Wallet.service;

import demo.Wallet.dto.ResponseModel;
import demo.Wallet.entity.User;
import demo.Wallet.exception.BadRequestException;
import demo.Wallet.exception.ErrorCodes;
import demo.Wallet.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    public ResponseEntity<ResponseModel<User>> registerUser(String username, String password, String email) {
        logger.info("User registration initiated for username: {}", username);

        // Check if username already exists
        Optional<User> existingUser = userRepository.findByUsername(username);
        if (existingUser.isPresent()) {
            logger.warn("User registration failed. Username already exists: {}", username);
            ResponseModel<User> responseModel = new ResponseModel<>(HttpStatus.BAD_REQUEST.value(), "Username already exists", null);
            return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
        }

        // Create new user
        User user = new User(username, password, email);
        User savedUser = userRepository.save(user);

        logger.info("User registered successfully with username: {}", username);

        ResponseModel<User> responseModel = new ResponseModel<>(HttpStatus.CREATED.value(), "User registered successfully", savedUser);
        return new ResponseEntity<>(responseModel, HttpStatus.CREATED);
    }

    public Optional<User> findByUsernameAndPassword(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, password);
    }

    public User validateUser(String username, String password) {
        return findByUsernameAndPassword(username, password).orElseThrow(() -> new BadRequestException("Invalid username or password", ErrorCodes.INVALID_USERNAME_OR_PASSWORD));
    }
}
