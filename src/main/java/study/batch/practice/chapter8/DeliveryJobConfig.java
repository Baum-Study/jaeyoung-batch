package study.batch.practice.chapter8;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.MyBatisBatchItemWriter;
import org.mybatis.spring.batch.MyBatisPagingItemReader;
import org.mybatis.spring.batch.builder.MyBatisBatchItemWriterBuilder;
import org.mybatis.spring.batch.builder.MyBatisPagingItemReaderBuilder;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.batch.item.support.builder.CompositeItemProcessorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class DeliveryJobConfig {
    
    private final SqlSessionFactory sqlSessionFactory;
    
    @Bean
    public MyBatisPagingItemReader<Item> deliveryReader() {
        return new MyBatisPagingItemReaderBuilder<Item>()
                .sqlSessionFactory(sqlSessionFactory)
                .pageSize(1000)
                .queryId("study.batch.mapper.ItemMapper.selectItemsPastDate")
                .build();
    }
    
    @Bean
    public MyBatisBatchItemWriter<Item> deliveryWriter() {
        return new MyBatisBatchItemWriterBuilder<Item>()
                .sqlSessionFactory(sqlSessionFactory)
                .statementId("study.batch.mapper.ItemMapper.updateItemDeliveryStatus")
                .build();
    }
    
    @Bean
    public CompositeItemProcessor<Item, Item> deliveryProcessor() {
        return new CompositeItemProcessorBuilder<Item, Item>()
                .delegates(new MileageProcessor())
                .build();
    }
    
    @Bean
    public Step processDeliveryStep(
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager
    ) {
        return new StepBuilder("processDeliveryStep", jobRepository)
                .<Item, Item>chunk(100, transactionManager)
                .reader(deliveryReader())
                .processor(deliveryProcessor())
                .writer(deliveryWriter())
                .build();
    }
    
    @Bean
    public Job processDeliveryJob(
            JobRepository jobRepository,
            Step processDeliveryStep
    ) {
        return new JobBuilder("processDeliveryJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(processDeliveryStep)
                .build();
    }
}
