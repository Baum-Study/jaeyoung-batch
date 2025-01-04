package study.batch.practice.chapter8;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MileageProcessor implements ItemProcessor<Item, Item> {
    
    private final ItemService itemService;
    
    @Override
    public Item process(Item item) {
        // 배송 완료된 경우 마일리지 추가
        if ('Y' == item.getDeliveredStatus()) {
            itemService.addMileage(item.getMemberId());
        }
        return item;
    }
}
