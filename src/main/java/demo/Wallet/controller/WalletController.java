package demo.Wallet.controller;

import demo.Wallet.dto.LoadMoneyRequest;
import demo.Wallet.dto.MakePaymentRequest;
import demo.Wallet.dto.ResponseModel;
import demo.Wallet.service.WalletService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wallets")
public class WalletController {
    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @PostMapping("/load-money")
    public ResponseEntity<ResponseModel> loadMoney(@RequestBody LoadMoneyRequest request) {
        return walletService.loadMoney(request.getUsername(), request.getPassword(), request.getAmount());
    }

    @PostMapping("/make-payment")
    public ResponseEntity<ResponseModel> makePayment(@RequestBody MakePaymentRequest request) {
        return walletService.makePayment(request.getPayerUsername(), request.getPayerPassword(), request.getPayeeUserId(), request.getAmount());
    }
}

