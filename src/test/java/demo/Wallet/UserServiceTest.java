package demo.Wallet;

import demo.Wallet.dto.ResponseModel;
import demo.Wallet.entity.User;
import demo.Wallet.exception.BadRequestException;
import demo.Wallet.repository.UserRepository;
import demo.Wallet.service.UserService;
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

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private String username;
    private String password;
    private String email;
    private User user;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        username = "testUser";
        password = "testPass";
        email = "test@test.com";
        user = new User(username, password, email);
    }

    @Test
    void registerUser_Success() {
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(user);

        ResponseEntity<ResponseModel<User>> response = userService.registerUser(username, password, email);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(Objects.requireNonNull(response.getBody()).getData());
        assertEquals(username, response.getBody().getData().getUsername());
    }

    @Test
    void registerUser_UsernameAlreadyExists() {
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        ResponseEntity<ResponseModel<User>> response = userService.registerUser(username, password, email);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(Objects.requireNonNull(response.getBody()).getData());
        assertEquals("Username already exists", response.getBody().getMessage());
    }

    @Test
    void validateUser_Success() {
        when(userRepository.findByUsernameAndPassword(username, password)).thenReturn(Optional.of(user));

        User validatedUser = userService.validateUser(username, password);

        assertNotNull(validatedUser);
        assertEquals(username, validatedUser.getUsername());
    }

    @Test
    void validateUser_InvalidCredentials() {
        when(userRepository.findByUsernameAndPassword(username, password)).thenReturn(Optional.empty());

        assertThrows(BadRequestException.class, () -> userService.validateUser(username, password));
    }
}
