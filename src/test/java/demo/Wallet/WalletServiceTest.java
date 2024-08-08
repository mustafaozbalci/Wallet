package demo.Wallet;

import demo.Wallet.dto.ResponseModel;
import demo.Wallet.entity.User;
import demo.Wallet.entity.Wallet;
import demo.Wallet.exception.BadRequestException;
import demo.Wallet.repository.WalletRepository;
import demo.Wallet.service.TransactionHistoryService;
import demo.Wallet.service.UserService;
import demo.Wallet.service.WalletService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

class WalletServiceTest {

    @Mock
    private UserService userService;

    @Mock
    private WalletRepository walletRepository;

    @Mock
    private TransactionHistoryService transactionHistoryService;

    @InjectMocks
    private WalletService walletService;

    private String username;
    private String password;
    private User user;
    private Wallet wallet;
    private Long payeeWalletId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        username = "testUser";
        password = "testPass";
        user = new User(username, password, "test@test.com");
        wallet = new Wallet(user, 50.0);
        payeeWalletId = 1L;
    }

    @Test
    void loadMoney_Success() {
        Double amount = 100.0;

        when(userService.validateUser(username, password)).thenReturn(user);
        when(walletRepository.findByUser(user)).thenReturn(Optional.of(wallet));
        when(walletRepository.save(any(Wallet.class))).thenReturn(wallet);

        ResponseEntity<ResponseModel<Wallet>> response = walletService.loadMoney(username, password, amount);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(Objects.requireNonNull(response.getBody()).getData());
        assertEquals(150.0, response.getBody().getData().getBalance());

    }

    @Test
    void loadMoney_WalletNotFound() {
        Double amount = 100.0;

        when(userService.validateUser(username, password)).thenReturn(user);
        when(walletRepository.findByUser(user)).thenReturn(Optional.empty());

        assertThrows(BadRequestException.class, () -> walletService.loadMoney(username, password, amount));

    }

    @Test
    void makePayment_Success() {
        Double amount = 50.0;
        Wallet payeeWallet = new Wallet();
        payeeWallet.setId(payeeWalletId);
        payeeWallet.setBalance(200.0);

        when(userService.validateUser(username, password)).thenReturn(user);
        when(walletRepository.findByUser(user)).thenReturn(Optional.of(wallet));
        when(walletRepository.findById(payeeWalletId)).thenReturn(Optional.of(payeeWallet));
        when(walletRepository.save(any(Wallet.class))).thenReturn(wallet);
        when(walletRepository.save(any(Wallet.class))).thenReturn(payeeWallet);

        ResponseEntity<ResponseModel<Void>> response = walletService.makePayment(username, password, payeeWalletId, amount);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertEquals(0.0, wallet.getBalance());
        assertEquals(250.0, payeeWallet.getBalance());

    }

    @Test
    void makePayment_InsufficientBalance() {
        Double amount = 150.0;

        when(userService.validateUser(username, password)).thenReturn(user);
        when(walletRepository.findByUser(user)).thenReturn(Optional.of(wallet));

        assertThrows(BadRequestException.class, () -> walletService.makePayment(username, password, payeeWalletId, amount));

    }

    @Test
    void makePayment_PayeeWalletNotFound() {
        Double amount = 50.0;

        when(userService.validateUser(username, password)).thenReturn(user);
        when(walletRepository.findByUser(user)).thenReturn(Optional.of(wallet));
        when(walletRepository.findById(payeeWalletId)).thenReturn(Optional.empty());

        assertThrows(BadRequestException.class, () -> walletService.makePayment(username, password, payeeWalletId, amount));

    }
}
