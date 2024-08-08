package demo.Wallet.controller;

import demo.Wallet.dto.LoadMoneyRequest;
import demo.Wallet.dto.MakePaymentRequest;
import demo.Wallet.dto.ResponseModel;
import demo.Wallet.entity.Wallet;
import demo.Wallet.service.WalletService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wallets")
public class WalletController {
    private static final Logger logger = LoggerFactory.getLogger(WalletController.class);

    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @PostMapping("/load-money")
    public ResponseEntity<ResponseModel<Wallet>> loadMoney(@RequestHeader("username") String username, @RequestHeader("password") String password, @RequestBody LoadMoneyRequest request) {
        logger.info("Load money request received for Username: {}, Amount: {}", username, request.getAmount());

        if (username == null || password == null) {
            logger.warn("Username or password is missing in the headers");
            return ResponseEntity.badRequest().body(new ResponseModel<>(HttpStatus.BAD_REQUEST.value(), "Username or password is missing", null));
        }

        ResponseEntity<ResponseModel<Wallet>> response = walletService.loadMoney(username, password, request.getAmount());

        logger.info("Load money response: {}", response.getBody().getMessage());
        return response;
    }

    @PostMapping("/make-payment")
    public ResponseEntity<ResponseModel<Void>> makePayment(@RequestHeader("payerUsername") String payerUsername, @RequestHeader("payerPassword") String payerPassword, @RequestBody MakePaymentRequest request) {
        logger.info("Make payment request received from username: {} to walletId: {}", payerUsername, request.getPayeeWalletId());

        if (payerUsername == null || payerPassword == null) {
            logger.warn("Payer username or password is missing in the headers");
            return ResponseEntity.badRequest().body(new ResponseModel<>(HttpStatus.BAD_REQUEST.value(), "Payer username or password is missing", null));
        }

        ResponseEntity<ResponseModel<Void>> response = walletService.makePayment(payerUsername, payerPassword, request.getPayeeWalletId(), request.getAmount());

        logger.info("Make payment response: {}", response.getBody().getMessage());
        return response;
    }
}
