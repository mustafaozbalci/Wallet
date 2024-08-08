package demo.Wallet.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class TransactionHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long walletIdFrom;
    private Long walletIdTo;
    private Double amount;
    private LocalDateTime transactionTime;

    public TransactionHistory(Long walletIdFrom, Long walletIdTo, Double amount, LocalDateTime transactionTime) {
        this.walletIdFrom = walletIdFrom;
        this.walletIdTo = walletIdTo;
        this.amount = amount;
        this.transactionTime = transactionTime;
    }
}
