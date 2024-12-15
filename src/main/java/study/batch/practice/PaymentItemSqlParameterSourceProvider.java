package study.batch.practice;

import org.springframework.batch.item.database.ItemSqlParameterSourceProvider;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import java.time.format.DateTimeFormatter;

public class PaymentItemSqlParameterSourceProvider implements ItemSqlParameterSourceProvider<Payment> {
    
    private final boolean isMonthly;
    
    public PaymentItemSqlParameterSourceProvider(boolean isMonthly) {
        this.isMonthly = isMonthly;
    }
    
    @Override
    public SqlParameterSource createSqlParameterSource(Payment payment) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("sellerId", payment.getSellerId());
        
        DateTimeFormatter formatter;
        if (isMonthly) {
            formatter = DateTimeFormatter.ofPattern("yyyy-MM");
        } else {
            formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        }
        
        String formattedDate = payment.getPaymentDate().format(formatter);
        
        parameterSource.addValue("paymentDate", formattedDate);
        parameterSource.addValue("price", payment.getPrice());
        
        return parameterSource;
    }
}
