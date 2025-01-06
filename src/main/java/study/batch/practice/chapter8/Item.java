package study.batch.practice.chapter8;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class Item {
    private Long itemId;
    
    private String itemName;
    
    private LocalDate deliveredAt;
    
    private char deliveredStatus = 'N';
    
    private Long memberId;
}
