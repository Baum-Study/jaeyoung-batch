package study.batch.practice.chapter5;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Payment {
    private long sellerId;
    private long productId;
    private String productName;
    private int price;
    private LocalDateTime paymentDate;
}
