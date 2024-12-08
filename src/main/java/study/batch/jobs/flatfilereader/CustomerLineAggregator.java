package study.batch.jobs.flatfilereader;

import org.springframework.batch.item.file.transform.LineAggregator;
import study.batch.jobs.model.Customer;

public class CustomerLineAggregator implements LineAggregator<Customer> {
    
    @Override
    public String aggregate(Customer item) {
        return item.getName() + "," + item.getAge();
    }
}
