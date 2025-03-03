package study.batch.chapter10;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
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
public class NextStepTaskJobConfiguration {
    
    public static final String NEXT_STEP_TASK = "NEXT_STEP_TASK";
    
    @Bean(name = "step01")
    public Step step01(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        log.info("------------------ Init myStep -----------------");
        return new StepBuilder("step01", jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    log.info("Execute Step 01 Tasklet ...");
                    return RepeatStatus.FINISHED;
                }, transactionManager)
                .build();
    }
    
    @Bean(name = "step02")
    public Step step02(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        log.info("------------------ Init myStep -----------------");
        return new StepBuilder("step02", jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    log.info("Execute Step 02 Tasklet ...");
                    return RepeatStatus.FINISHED;
                }, transactionManager)
                .build();
    }
    
    @Bean
    public Job nextStepJob(Step step01, Step step02, JobRepository jobRepository) {
        log.info("------------------ Init myJob -----------------");
        return new JobBuilder(NEXT_STEP_TASK, jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(step01)
                .next(step02)
                .build();
    }
}
