package demo.Wallet.dto;

import lombok.Data;

@Data
public class MakePaymentRequest {
    private Long payeeWalletId;
    private Double amount;
}
