package study.batch.chapter.jobs.flatfilereader;

import org.springframework.batch.item.file.transform.LineAggregator;
import study.batch.chapter.jobs.model.Customer;

public class CustomerLineAggregator implements LineAggregator<Customer> {
    
    @Override
    public String aggregate(Customer item) {
        return item.getName() + "," + item.getAge();
    }
}
