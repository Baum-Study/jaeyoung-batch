package study.batch;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import study.batch.practice.chapter8.DeliveryJobConfig;
import study.batch.practice.chapter8.ItemService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

@SpringBatchTest
@SpringBootTest
@Import(DeliveryJobConfig.class)
public class MyBatisReaderJobTest {
    
    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;
    
    @Autowired
    private JobLauncher jobLauncher;
    
    @Autowired
    private Job processDeliveryJob;
    
    @MockBean
    private ItemService itemService;
    
    @AfterEach
    void tearDown() {
        Mockito.reset(itemService);
    }
    
    @Test
    void testProcessDeliveryJob() throws Exception {
        // Given
        jobLauncher.run(processDeliveryJob, new JobParameters());
        doNothing().when(itemService).addMileage(any());
        
        // When
        JobExecution jobExecution = jobLauncherTestUtils.launchJob();
        
        // Then
        assertThat(jobExecution.getStatus()).isEqualTo(BatchStatus.COMPLETED); // 배치 작업 상태 확인
        verify(itemService, atLeastOnce()).addMileage(any()); // addMileage 메서드 호출 확인
    }
}
