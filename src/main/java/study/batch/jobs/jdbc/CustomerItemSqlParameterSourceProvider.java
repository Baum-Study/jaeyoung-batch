package study.batch.jobs.jdbc;

import org.springframework.batch.item.database.ItemSqlParameterSourceProvider;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import study.batch.jobs.model.Customer;

public class CustomerItemSqlParameterSourceProvider implements ItemSqlParameterSourceProvider<Customer> {
    
    @Override
    public SqlParameterSource createSqlParameterSource(Customer item) {
        return new BeanPropertySqlParameterSource(item);
    }
}