package study.batch;

import org.junit.jupiter.api.Test;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import study.batch.practice.chapter8.DeliveryJobConfig;
import study.batch.practice.chapter8.ItemService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

@SpringBootTest
@SpringBatchTest
@Import(DeliveryJobConfig.class)
class MyBatisReaderJobTest {
    
    @MockBean
    private ItemService itemService;
    
    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;
    
    @Test
    void testProcessDeliveryJob() throws Exception {
        // given
        Job job = jobLauncherTestUtils.getJob();
        assertThat(job).isNotNull();
        assertThat(job.getName()).isEqualTo("processDeliveryJob");
        
        doNothing().when(itemService).addMileage();
        
        // when
        JobExecution jobExecution = jobLauncherTestUtils.launchJob();
        
        // then
        assertThat(jobExecution.getStatus()).isEqualTo(BatchStatus.COMPLETED);
        verify(itemService, atLeastOnce()).addMileage();
    }
}
