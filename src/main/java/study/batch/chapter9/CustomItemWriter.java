package study.batch.chapter9;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;
import study.batch.chapter.jobs.model.Customer;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomItemWriter implements ItemWriter<Customer> {
    
    private final CustomService customService;
    
    @Override
    public void write(Chunk<? extends Customer> chunk) {
        log.info("Start Customer CustomItemWriter.");
        for (Customer customer : chunk) {
            customService.processToOtherService(customer);
        }
    }
}
