package study.batch.chapter11;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
public class TestJobConfiguration {
    
    public static final String LISTENER_TEST_STEP_TASK = "LISTENER_TEST_STEP_TASK";
    
    @Bean(name = "step03")
    public Step step03(JobRepository jobRepository, PlatformTransactionManager transactionManager, StepExecutionListener stepExecutionListener) {
        log.info("------------------ Init myStep -----------------");
        return new StepBuilder("step03", jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    log.info("Execute Step 03 Tasklet ...");
                    return RepeatStatus.FINISHED;
                }, transactionManager)
                .listener(stepExecutionListener)
                .build();
    }
    
    @Bean(name = "step04")
    public Step step04(JobRepository jobRepository, PlatformTransactionManager transactionManager, StepExecutionListener stepExecutionListener) {
        log.info("------------------ Init myStep -----------------");
        return new StepBuilder("step04", jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    log.info("Execute Step 04 Tasklet ...");
                    return RepeatStatus.FINISHED;
                }, transactionManager)
                .listener(stepExecutionListener)
                .build();
    }
    
    @Bean
    public Job testJob(Step step03, Step step04, JobRepository jobRepository, JobExecutionListener jobExecutionListener) {
        log.info("------------------ Init myJob -----------------");
        return new JobBuilder(LISTENER_TEST_STEP_TASK, jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(step03)
                .next(step04)
                .listener(jobExecutionListener)
                .build();
    }
}
