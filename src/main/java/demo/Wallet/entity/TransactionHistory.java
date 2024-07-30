package demo.Wallet.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class TransactionHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long walletIdFrom;
    private Long walletIdTo;
    private Double amount;
    private LocalDateTime transactionTime;
}
