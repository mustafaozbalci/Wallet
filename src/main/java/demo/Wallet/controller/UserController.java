package demo.Wallet.controller;


import demo.Wallet.dto.ResponseModel;
import demo.Wallet.entity.User;
import demo.Wallet.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseModel> registerUser(@RequestBody User user) {
        return userService.registerUser(user);
    }


}
