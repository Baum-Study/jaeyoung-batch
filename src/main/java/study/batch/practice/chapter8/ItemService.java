package study.batch.practice.chapter8;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemService {
    
    public void addMileage() {
        log.info("add mileage");
    }
}
