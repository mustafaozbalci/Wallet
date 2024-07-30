package demo.Wallet.service;

import demo.Wallet.dto.ResponseModel;
import demo.Wallet.entity.User;
import demo.Wallet.entity.Wallet;
import demo.Wallet.repository.UserRepository;
import demo.Wallet.repository.WalletRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;


    private final WalletRepository walletRepository;

    public UserService(UserRepository userRepository, WalletRepository walletRepository) {
        this.userRepository = userRepository;
        this.walletRepository = walletRepository;
    }

    public ResponseEntity<ResponseModel> registerUser(User user) {
        User savedUser = userRepository.save(user);
        Wallet wallet = new Wallet();
        wallet.setUser(savedUser);
        wallet.setBalance(500.0);
        walletRepository.save(wallet);

        ResponseModel responseModel = new ResponseModel(HttpStatus.CREATED.value(), "User registered successfully", savedUser);
        return new ResponseEntity<>(responseModel, HttpStatus.CREATED);
    }
}
