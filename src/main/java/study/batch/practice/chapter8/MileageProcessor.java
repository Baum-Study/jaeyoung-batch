package study.batch.practice.chapter8;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class MileageProcessor implements ItemProcessor<Item, Item> {
    
    @Override
    public Item process(Item item) {
        ItemService itemService = new ItemService();
        itemService.addMileage();
        return item;
    }
}
