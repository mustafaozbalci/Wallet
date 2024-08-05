package demo.Wallet.controller;

import demo.Wallet.dto.LoadMoneyRequest;
import demo.Wallet.dto.MakePaymentRequest;
import demo.Wallet.dto.ResponseModel;
import demo.Wallet.service.WalletService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wallets")
public class WalletController {
    private static final Logger logger = LoggerFactory.getLogger(WalletController.class);

    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @PostMapping("/load-money")
    public ResponseEntity<ResponseModel> loadMoney(@RequestBody LoadMoneyRequest request) {
        logger.info("Load money request received for username: {}", request.getUsername());
        ResponseEntity<ResponseModel> response = walletService.loadMoney(request.getUsername(), request.getPassword(), request.getAmount());
        logger.info("Load money response: {}", response.getBody().getMessage());
        return response;
    }

    @PostMapping("/make-payment")
    public ResponseEntity<ResponseModel> makePayment(@RequestBody MakePaymentRequest request) {
        logger.info("Make payment request received from username: {} to userId: {}", request.getPayerUsername(), request.getPayeeUserId());
        ResponseEntity<ResponseModel> response = walletService.makePayment(request.getPayerUsername(), request.getPayerPassword(), request.getPayeeUserId(), request.getAmount());
        logger.info("Make payment response: {}", response.getBody().getMessage());
        return response;
    }
}
