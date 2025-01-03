package study.batch.chapter8;

import org.springframework.batch.item.ItemProcessor;
import study.batch.chapter.jobs.model.Customer;

/**
 * 이름, 성별을 소문자로 변경하는 ItemProcessor
 */
public class LowerCaseItemProcessor implements ItemProcessor<Customer, Customer> {
    @Override
    public Customer process(Customer item) {
        item.setName(item.getName().toLowerCase());
        item.setGender(item.getGender().toLowerCase());
        return item;
    }
}
