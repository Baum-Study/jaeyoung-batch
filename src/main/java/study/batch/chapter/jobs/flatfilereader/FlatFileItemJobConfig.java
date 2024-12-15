//package study.batch.chapter.jobs.flatfilereader;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.job.builder.JobBuilder;
//import org.springframework.batch.core.launch.support.RunIdIncrementer;
//import org.springframework.batch.core.repository.JobRepository;
//import org.springframework.batch.core.step.builder.StepBuilder;
//import org.springframework.batch.item.ItemProcessor;
//import org.springframework.batch.item.file.FlatFileItemReader;
//import org.springframework.batch.item.file.FlatFileItemWriter;
//import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
//import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.core.io.FileSystemResource;
//import org.springframework.transaction.PlatformTransactionManager;
//import study.batch.chapter.jobs.model.Customer;
//
//import java.util.concurrent.ConcurrentHashMap;
//
//@Slf4j
//@Configuration
//public class FlatFileItemJobConfig {
//
//    public static final int CHUNK_SIZE = 100;
//    public static final String ENCODING_TYPE = "UTF-8";
//    private static final String FLAT_FILE_WRITER_CHUNK_JOB = "FLAT_FILE_WRITER_CHUNK_JOB";
//
//    private ConcurrentHashMap<String, Integer> aggregateInfos = new ConcurrentHashMap<>();
//
//    private final ItemProcessor<Customer, Customer> itemProcessor = new AggregateCustomerProcessor(aggregateInfos);
//
//    @Bean
//    public FlatFileItemReader<Customer> flatFileItemReader() {
//        return new FlatFileItemReaderBuilder<Customer>()
//                .name("FlatFileItemReader")
//                .resource(new ClassPathResource("./customer.csv"))
//                .encoding(ENCODING_TYPE)
//                .delimited().delimiter(",")
//                .names("name", "age", "gender")
//                .targetType(Customer.class)
//                .build();
//    }
//
//    @Bean
//    public FlatFileItemWriter<Customer> flatFileItemWriter() {
//        return new FlatFileItemWriterBuilder<Customer>()
//                .name("FlatFileItemWriter")
//                .resource(new FileSystemResource("./output/customer_new.csv"))
//                .encoding(ENCODING_TYPE)
//                .delimited().delimiter("\t")
//                .names("Name", "Age", "Gender")
//                .append(false)
//                .lineAggregator(new CustomerLineAggregator())
//                .headerCallback(new CustomerHeader())
//                .footerCallback(new CustomerFooter(aggregateInfos))
//                .build();
//    }
//
//    @Bean
//    public Step flatFileStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
//        log.info("--------------- Init flatFileStep ---------------");
//        return new StepBuilder("flatFileStep", jobRepository)
//                .<Customer, Customer>chunk(CHUNK_SIZE, transactionManager)
//                .reader(flatFileItemReader())
//                .processor(itemProcessor)
//                .writer(flatFileItemWriter())
//                .build();
//    }
//
//    @Bean
//    public Job flatFileJob(Step flatFileStep, JobRepository jobRepository) {
//        log.info("--------------- Init flatFileJob ---------------");
//        return new JobBuilder(FLAT_FILE_WRITER_CHUNK_JOB, jobRepository)
//                .incrementer(new RunIdIncrementer())
//                .start(flatFileStep)
//                .build();
//    }
//}