package demo.Wallet.controller;

import demo.Wallet.dto.ResponseModel;
import demo.Wallet.entity.User;
import demo.Wallet.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseModel> registerUser(@RequestBody User user) {
        logger.info("User registration request received for username: {}", user.getUsername());
        ResponseEntity<ResponseModel> response = userService.registerUser(user);
        logger.info("User registration response: {}", response.getBody().getMessage());
        return response;
    }
}
