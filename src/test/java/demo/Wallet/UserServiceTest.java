//package demo.Wallet.service;
//
//import demo.Wallet.dto.ResponseModel;
//import demo.Wallet.entity.User;
//import demo.Wallet.entity.Wallet;
//import demo.Wallet.repository.UserRepository;
//import demo.Wallet.repository.WalletRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//class UserServiceTest {
//
//    @Mock
//    private UserRepository userRepository;
//    @Mock
//    private WalletRepository walletRepository;
//    private UserService userService;
//
//    @BeforeEach
//    void setUp() {
//        userRepository = mock(UserRepository.class);
//        walletRepository = mock(WalletRepository.class);
//        userService = new UserService(userRepository, walletRepository);
//    }
//
//    @Test
//    void registerUser_Success() {
//        String username = "tester";
//        String password = "password";
//        String email = "test@example.com";
//
//        User user = new User();
//        user.setUsername(username);
//        user.setPassword(password);
//        user.setEmail(email);
//
//        Wallet wallet = new Wallet();
//        wallet.setUser(user);
//        wallet.setBalance(0.0);
//
//        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());
//        when(userRepository.save(any(User.class))).thenReturn(user);
//        when(walletRepository.save(any(Wallet.class))).thenReturn(wallet);
//
//        ResponseEntity<ResponseModel> response = userService.registerUser(username, password, email);
//
//        assertEquals(HttpStatus.CREATED, response.getStatusCode());
//        assertNotNull(response.getBody());
//        assertEquals("User registered successfully", response.getBody().getMessage());
//    }
//
//    @Test
//    void registerUser_UserAlreadyExists() {
//        // Given
//        String username = "tester";
//        String password = "password";
//        String email = "test@example.com";
//
//        User existingUser = new User();
//        existingUser.setUsername(username);
//
//        when(userRepository.findByUsername(username)).thenReturn(Optional.of(existingUser));
//
//        // When
//        ResponseEntity<ResponseModel> response = userService.registerUser(username, password, email);
//
//        // Then
//        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
//        assertNotNull(response.getBody());
//        assertEquals("Username already exists", response.getBody().getMessage());
//    }
//}
