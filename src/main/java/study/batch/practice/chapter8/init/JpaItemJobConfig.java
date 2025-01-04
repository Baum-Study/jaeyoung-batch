//package study.batch.practice.chapter8.init;
//
//import jakarta.persistence.EntityManagerFactory;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.job.builder.JobBuilder;
//import org.springframework.batch.core.launch.support.RunIdIncrementer;
//import org.springframework.batch.core.repository.JobRepository;
//import org.springframework.batch.core.step.builder.StepBuilder;
//import org.springframework.batch.item.database.JpaItemWriter;
//import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
//import org.springframework.batch.item.file.FlatFileItemReader;
//import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.FileSystemResource;
//import org.springframework.transaction.PlatformTransactionManager;
//import study.batch.practice.chapter8.Item;
//
//@Slf4j
//@Configuration
//@RequiredArgsConstructor
//public class JpaItemJobConfig {
//
//    public static final int CHUNK_SIZE = 1000;
//    public static final String ENCODING = "UTF-8";
//    public static final String JPA_ITEM_WRITER_JOB = "JPA_ITEM_WRITER_JOB";
//
//    private final EntityManagerFactory entityManagerFactory;
//
//    @Bean
//    public FlatFileItemReader<Item> flatFileItemReader() {
//        return new FlatFileItemReaderBuilder<Item>()
//                .name("FlatFileItemReader")
//                .resource(new FileSystemResource("./input/item.csv"))
//                .encoding(ENCODING)
//                .lineMapper(new ItemLineMapper())
//                .build();
//    }
//
//    @Bean
//    public JpaItemWriter<Item> jpaItemWriter() {
//        return new JpaItemWriterBuilder<Item>()
//                .entityManagerFactory(entityManagerFactory)
//                .usePersist(true)
//                .build();
//    }
//
//    @Bean
//    public Step flatFileStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
//        log.info("------------------ Init flatFileStep -----------------");
//        return new StepBuilder("flatFileStep", jobRepository)
//                .<Item, Item>chunk(CHUNK_SIZE, transactionManager)
//                .reader(flatFileItemReader())
//                .writer(jpaItemWriter())
//                .build();
//    }
//
//    @Bean
//    public Job flatFileJob(Step flatFileStep, JobRepository jobRepository) {
//        log.info("------------------ Init flatFileJob -----------------");
//        return new JobBuilder(JPA_ITEM_WRITER_JOB, jobRepository)
//                .incrementer(new RunIdIncrementer())
//                .start(flatFileStep)
//                .build();
//    }
//}
