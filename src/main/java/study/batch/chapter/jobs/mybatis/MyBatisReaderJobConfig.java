//package study.batch.chapter.jobs.mybatis;
//
//import lombok.extern.slf4j.Slf4j;
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.mybatis.spring.batch.MyBatisPagingItemReader;
//import org.mybatis.spring.batch.builder.MyBatisPagingItemReaderBuilder;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.job.builder.JobBuilder;
//import org.springframework.batch.core.launch.support.RunIdIncrementer;
//import org.springframework.batch.core.repository.JobRepository;
//import org.springframework.batch.core.step.builder.StepBuilder;
//import org.springframework.batch.item.file.FlatFileItemWriter;
//import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.FileSystemResource;
//import org.springframework.transaction.PlatformTransactionManager;
//import study.batch.chapter.jobs.jpa.CustomerItemProcessor;
//import study.batch.chapter.jobs.model.Customer;
//
//import javax.sql.DataSource;
//
//@Slf4j
//@Configuration
//public class MyBatisReaderJobConfig {
//
//    /**
//     * CHUNK 크기를 지정한다.
//     */
//    public static final int CHUNK_SIZE = 2;
//    public static final String ENCODING = "UTF-8";
//    public static final String MYBATIS_CHUNK_JOB = "MYBATIS_CHUNK_JOB";
//
//    @Autowired
//    DataSource dataSource;
//
//    @Autowired
//    SqlSessionFactory sqlSessionFactory;
//
//    @Bean
//    public MyBatisPagingItemReader<Customer> myBatisItemReader() {
//        return new MyBatisPagingItemReaderBuilder<Customer>()
//                .sqlSessionFactory(sqlSessionFactory)
//                .pageSize(CHUNK_SIZE)
//                .queryId("study.")
//                .build();
//    }
//
//    @Bean
//    public FlatFileItemWriter<Customer> customerCursorFlatFileItemWriter() {
//        return new FlatFileItemWriterBuilder<Customer>()
//                .name("customerCursorFlatFileItemWriter")
//                .resource(new FileSystemResource("./output/customer_new_v4.csv"))
//                .encoding(ENCODING)
//                .delimited().delimiter("\t")
//                .names("Name", "Age", "Gender")
//                .build();
//    }
//
//    @Bean
//    public Step customerJdbcCursorStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) throws Exception {
//        log.info("------------------ Init customerJdbcCursorStep -----------------");
//
//        return new StepBuilder("customerJdbcCursorStep", jobRepository)
//                .<Customer, Customer>chunk(CHUNK_SIZE, transactionManager)
//                .reader(myBatisItemReader())
//                .processor(new CustomerItemProcessor())
//                .writer(customerCursorFlatFileItemWriter())
//                .build();
//    }
//
//    @Bean
//    public Job customerJdbcCursorPagingJob(Step customerJdbcCursorStep, JobRepository jobRepository) {
//        log.info("------------------ Init customerJdbcCursorPagingJob -----------------");
//        return new JobBuilder(MYBATIS_CHUNK_JOB, jobRepository)
//                .incrementer(new RunIdIncrementer())
//                .start(customerJdbcCursorStep)
//                .build();
//    }
//}
//
