package demo.Wallet.controller;

import demo.Wallet.dto.RegisterUserRequest;
import demo.Wallet.dto.ResponseModel;
import demo.Wallet.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseModel> registerUser(@RequestHeader("username") String username, @RequestHeader("password") String password, @RequestBody RegisterUserRequest request) {
        if (request.getEmail() == null) {
            logger.warn("Email is missing in the request body");
            return ResponseEntity.badRequest().body(new ResponseModel(400, "Email is missing", null));
        }
        logger.info("User registration request received for username: {}", username);
        ResponseEntity<ResponseModel> response = userService.registerUser(username, password, request.getEmail());
        logger.info("User registration response: {}", response.getBody().getMessage());
        return response;
    }
}
