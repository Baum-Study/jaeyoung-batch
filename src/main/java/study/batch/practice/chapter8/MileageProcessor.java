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
        itemService.addMileage();
        return item;
    }
}
