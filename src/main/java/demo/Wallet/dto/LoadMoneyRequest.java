package demo.Wallet.dto;

import lombok.Data;

@Data
public class LoadMoneyRequest {
    private String username;
    private String password;
    private Double amount;
}
