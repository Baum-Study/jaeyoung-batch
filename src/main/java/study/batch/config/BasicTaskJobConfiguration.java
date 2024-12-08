//package study.batch.config;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.job.builder.JobBuilder;
//import org.springframework.batch.core.launch.support.RunIdIncrementer;
//import org.springframework.batch.core.repository.JobRepository;
//import org.springframework.batch.core.step.builder.StepBuilder;
//import org.springframework.batch.core.step.tasklet.Tasklet;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.transaction.PlatformTransactionManager;
//import study.batch.jobs.task01.GreetingTask;
//
//@Slf4j
//@Configuration
//@RequiredArgsConstructor
//public class BasicTaskJobConfiguration {
//
//    private final PlatformTransactionManager transactionManager;
//    private final JobRepository jobRepository;
//
//    @Bean
//    public Tasklet greetingTasklet() {
//        return new GreetingTask();
//    }
//
//    @Bean
//    public Step step() {
//        log.info("------------------ Init myStep -----------------");
//
//        return new StepBuilder("myStep", jobRepository)
//                .tasklet(greetingTasklet(), transactionManager)
//                .build();
//    }
//
//    @Bean
//    public Job myJob() {
//        log.info("------------------ Init myJob -----------------");
//
//        return new JobBuilder("myJob", jobRepository)
//                .incrementer(new RunIdIncrementer())
//                .start(step())
//                .build();
//    }
//}
