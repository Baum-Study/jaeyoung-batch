//package study.batch.practice;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.job.builder.JobBuilder;
//import org.springframework.batch.core.launch.support.RunIdIncrementer;
//import org.springframework.batch.core.repository.JobRepository;
//import org.springframework.batch.core.step.builder.StepBuilder;
//import org.springframework.batch.item.database.JdbcBatchItemWriter;
//import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
//import org.springframework.batch.item.file.FlatFileItemReader;
//import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.FileSystemResource;
//import org.springframework.transaction.PlatformTransactionManager;
//
//import javax.sql.DataSource;
//
//@Slf4j
//@Configuration
//@RequiredArgsConstructor
//public class JdbcBatchItemJobConfig {
//
//    public static final int CHUNK_SIZE = 100;
//    public static final String ENCODING = "UTF-8";
//    public static final String JDBC_BATCH_WRITER_CHUNK_JOB = "JDBC_BATCH_WRITER_CHUNK_JOB";
//
//    private final DataSource dataSource;
//
//    @Bean
//    public FlatFileItemReader<Payment> monthlySettlementReader() {
//        return new FlatFileItemReaderBuilder<Payment>()
//                .name("monthlySettlementReader")
//                .resource(new FileSystemResource("./input/payments.csv"))
//                .encoding(ENCODING)
//                .lineMapper(new PaymentLineMapper(true)) // 월별 정산을 위한 LineMapper
//                .build();
//    }
//
//    @Bean
//    public FlatFileItemReader<Payment> dailySettlementReader() {
//        return new FlatFileItemReaderBuilder<Payment>()
//                .name("dailySettlementReader")
//                .resource(new FileSystemResource("./input/payments.csv"))
//                .encoding(ENCODING)
//                .lineMapper(new PaymentLineMapper(false)) // 일별 정산을 위한 LineMapper
//                .build();
//    }
//
//    @Bean
//    public JdbcBatchItemWriter<Payment> monthlySettlementWriter() {
//        return new JdbcBatchItemWriterBuilder<Payment>()
//                .dataSource(dataSource)
//                .sql("INSERT INTO MONTHLY_SETTLEMENT (seller_id, settlement_month, total_amount) " +
//                        "VALUES (:sellerId, :paymentDate, :price) " +
//                        "ON DUPLICATE KEY UPDATE " +
//                        "total_amount = total_amount + :price;")
//                .itemSqlParameterSourceProvider(new PaymentItemSqlParameterSourceProvider(true))
//                .build();
//    }
//
//    @Bean
//    public JdbcBatchItemWriter<Payment> dailySettlementWriter() {
//        return new JdbcBatchItemWriterBuilder<Payment>()
//                .dataSource(dataSource)
//                .sql("INSERT INTO DAILY_SETTLEMENT (seller_id, settlement_date, total_amount) " +
//                        "VALUES (:sellerId, :paymentDate, :price) " +
//                        "ON DUPLICATE KEY UPDATE " +
//                        "total_amount = total_amount + :price;")
//                .itemSqlParameterSourceProvider(new PaymentItemSqlParameterSourceProvider(false))
//                .build();
//    }
//
//    @Bean
//    public Step monthlySettlementStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
//        return new StepBuilder("monthlySettlementStep", jobRepository)
//                .<Payment, Payment>chunk(CHUNK_SIZE, transactionManager)
//                .reader(monthlySettlementReader())
//                .writer(monthlySettlementWriter())
//                .build();
//    }
//
//    @Bean
//    public Step dailySettlementStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
//        return new StepBuilder("dailySettlementStep", jobRepository)
//                .<Payment, Payment>chunk(CHUNK_SIZE, transactionManager)
//                .reader(dailySettlementReader())
//                .writer(dailySettlementWriter())
//                .build();
//    }
//
//    @Bean
//    public Job flatFileJob(Step monthlySettlementStep, Step dailySettlementStep, JobRepository jobRepository) {
//        return new JobBuilder(JDBC_BATCH_WRITER_CHUNK_JOB, jobRepository)
//                .incrementer(new RunIdIncrementer())
//                .start(monthlySettlementStep)
//                .next(dailySettlementStep)
//                .build();
//    }
//}
