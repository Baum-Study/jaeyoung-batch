package study.batch.practice.chapter5;

import org.springframework.batch.item.file.LineMapper;

import java.time.LocalDateTime;

public class PaymentLineMapper implements LineMapper<Payment> {
    
    private boolean isMonthly;
    
    // 월/일별 정산 여부
    public PaymentLineMapper(boolean isMonthly) {
        this.isMonthly = isMonthly;
    }
    
    @Override
    public Payment mapLine(String line, int lineNumber) {
        line = line.replace("Payment(", "").replace(")", "");
        String[] keyValues = line.split(", ");
        
        Payment payment = new Payment();
        for (String keyValue : keyValues) {
            String[] entry = keyValue.split("=");
            switch (entry[0]) {
                case "sellerId":
                    payment.setSellerId(Long.parseLong(entry[1]));
                    break;
                case "productId":
                    payment.setProductId(Long.parseLong(entry[1]));
                    break;
                case "productName":
                    payment.setProductName(entry[1]);
                    break;
                case "price":
                    payment.setPrice(Integer.parseInt(entry[1]));
                    break;
                case "paymentDate":
                    String paymentDateStr = entry[1];
                    
                    if (isMonthly) {
                        paymentDateStr = paymentDateStr.substring(0, paymentDateStr.indexOf("T") - 3);
                        payment.setPaymentDate(LocalDateTime.parse(paymentDateStr + "-01T00:00:00"));
                    } else {
                        paymentDateStr = paymentDateStr.substring(0, paymentDateStr.indexOf("T"));
                        payment.setPaymentDate(LocalDateTime.parse(paymentDateStr + "T00:00:00"));
                    }
                    break;
            }
        }
        return payment;
    }
}
