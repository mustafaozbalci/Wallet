package demo.Wallet.dto;

import lombok.Data;

@Data
public class MakePaymentRequest {
    private String payerUsername;
    private String payerPassword;
    private Long payeeUserId;
    private Double amount;
}
