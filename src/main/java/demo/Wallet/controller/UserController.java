package demo.Wallet.controller;

import demo.Wallet.dto.RegisterUserRequest;
import demo.Wallet.dto.ResponseModel;
import demo.Wallet.entity.User;
import demo.Wallet.service.UserService;
import demo.Wallet.service.WalletService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;
    private final WalletService walletService;

    public UserController(UserService userService, WalletService walletService) {
        this.userService = userService;
        this.walletService = walletService;
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseModel<User>> registerUser(@RequestBody RegisterUserRequest request) {
        if (request.getUsername() == null || request.getPassword() == null || request.getEmail() == null) {
            logger.warn("Username, password, or email is missing in the request body");
            return ResponseEntity.badRequest().body(new ResponseModel<>(400, "Username, password, and email are required", null));
        }
        logger.info("User registration request received for username: {}", request.getUsername());

        ResponseEntity<ResponseModel<User>> userResponse = userService.registerUser(request.getUsername(), request.getPassword(), request.getEmail());
        if (userResponse.getStatusCode() == HttpStatus.CREATED) {
            User newUser = userResponse.getBody().getData();
            walletService.createWallet(newUser);
        }

        logger.info("User registration response: {}", userResponse.getBody().getMessage());
        return userResponse;
    }
}
