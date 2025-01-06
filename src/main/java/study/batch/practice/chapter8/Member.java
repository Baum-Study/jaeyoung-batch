package study.batch.practice.chapter8;

import lombok.Getter;
import lombok.Setter;
import study.batch.practice.chapter6.model.MemberStatus;

@Getter
@Setter
public class Member {
    private Long memberId;
    
    private MemberStatus status;
    
    private String name;
    
    private int mileagePoint = 0;
}
