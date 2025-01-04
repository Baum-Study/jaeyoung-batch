package study.batch.practice.chapter8.init;

import org.springframework.batch.item.file.LineMapper;
import study.batch.practice.chapter8.Item;
import study.batch.practice.chapter8.Member;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ItemLineMapper implements LineMapper<Item> {
    
    @Override
    public Item mapLine(String line, int lineNumber) {
        // 정규식을 사용해 데이터 추출
        String regex = "Item\\(itemName=([a-zA-Z0-9\\-]+), arrivedAt=(\\d{4}-\\d{2}-\\d{2}), memberId=(\\d+)\\)";
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(regex);
        java.util.regex.Matcher matcher = pattern.matcher(line);
        
        if (matcher.matches()) {
            // 매칭된 데이터에서 값 추출
            String itemName = matcher.group(1);
            String arrivedAt = matcher.group(2);
            String memberId = matcher.group(3);
            
            // Item 객체 생성 및 데이터 설정
            Item item = new Item();
            item.setItemName(itemName);
            item.setDeliveredAt(LocalDate.parse(arrivedAt, DateTimeFormatter.ISO_LOCAL_DATE));
            item.setDeliveredStatus('N');
            
            // Member 객체 생성 및 설정
            Member member = new Member();
            member.setMemberId(Long.parseLong(memberId));
//            item.setMember(member);
            
            return item;
        } else {
            // 데이터 형식이 맞지 않을 경우 예외 발생
            throw new IllegalArgumentException("Invalid line format: " + line);
        }
    }
}
