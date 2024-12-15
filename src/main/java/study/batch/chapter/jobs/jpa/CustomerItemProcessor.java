package study.batch.chapter.jobs.jpa;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import study.batch.chapter.jobs.model.Customer;

@Slf4j
public class CustomerItemProcessor implements ItemProcessor<Customer, Customer> {

    @Override
    public Customer process(Customer item) {
        log.info("Item Processor ------------------- {}", item);
        return item;
    }
}
