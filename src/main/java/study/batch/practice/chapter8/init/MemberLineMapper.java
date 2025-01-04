package study.batch.practice.chapter8.init;

import org.springframework.batch.item.file.LineMapper;
import study.batch.practice.chapter6.model.MemberStatus;
import study.batch.practice.chapter8.Member;

public class MemberLineMapper implements LineMapper<Member> {
    @Override
    public Member mapLine(String line, int lineNumber) {
        // 정규식을 사용해 데이터 추출
        String regex = "Member\\(memberId=null, status=(\\w+), name=(.+)\\)";
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(regex);
        java.util.regex.Matcher matcher = pattern.matcher(line);
        
        if (matcher.matches()) {
            Member member = new Member();
            member.setStatus(MemberStatus.valueOf(matcher.group(1))); // status
            member.setName(matcher.group(2)); // name
            return member;
        } else {
            throw new IllegalArgumentException("Invalid line format: " + line);
        }
    }
}
