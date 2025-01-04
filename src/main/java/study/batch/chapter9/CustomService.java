package study.batch.chapter9;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import study.batch.chapter.jobs.model.Customer;

@Slf4j
@Service
public class CustomService {
    
    public void processToOtherService(Customer customer) {
        log.info("Call API to OtherService of customer:{}", customer.getName());
    }
}
