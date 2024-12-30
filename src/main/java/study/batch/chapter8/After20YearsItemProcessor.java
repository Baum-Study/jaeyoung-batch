package study.batch.chapter8;

import org.springframework.batch.item.ItemProcessor;
import study.batch.chapter.jobs.model.Customer;

/**
 * 나이에 20년을 더하는 ItemProcessor
 */
public class After20YearsItemProcessor implements ItemProcessor<Customer, Customer> {
    @Override
    public Customer process(Customer item) {
        item.setAge(item.getAge() + 20);
        return item;
    }
}
