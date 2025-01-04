package study.batch.practice.chapter8;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class DeliveryStatusProcessor implements ItemProcessor<Item, Item> {
    
    private final ItemService itemService;
    
    @Override
    public Item process(Item item) {
        // 도착 상태 업데이트
        if (item.getDeliveredAt().isBefore(LocalDate.now())) {
            item.setDeliveredStatus('Y');
            itemService.addMileage(item.getMemberId());
        }
        return item;
    }
}
