package study.batch.practice.chapter8;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemService {
    
    private final MemberRepository memberRepository;
    
    public void addMileage(Long memberId) {
        log.info("Adding mileage for member: {}", memberId);
        
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));
        
        int additionalMileage = 100;
        member.setMileagePoint(member.getMileagePoint() + additionalMileage);
        
        // 마일리지 저장
        memberRepository.save(member);
        
        log.info("Mileage added for member: {}. New mileage: {}", memberId, member.getMileagePoint());
    }
}
