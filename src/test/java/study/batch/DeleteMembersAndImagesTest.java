//package study.batch;
//
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import study.batch.practice.chapter6.model.Image;
//import study.batch.practice.chapter6.model.Member;
//import study.batch.practice.chapter6.model.MemberStatus;
//import study.batch.practice.chapter6.repository.ImageRepository;
//import study.batch.practice.chapter6.repository.MemberRepository;
//
//import java.util.List;
//import java.util.Random;
//import java.util.stream.Collectors;
//import java.util.stream.IntStream;
//
//@SpringBootTest
//public class DeleteMembersAndImagesTest {
//
//    @Autowired
//    MemberRepository memberRepository;
//
//    @Autowired
//    ImageRepository imageRepository;
//
//    private static final String BASE_IMAGE_URL = "https://example.com/images";
//    private static final String IMAGE_TYPE = ".jpa";
//
//    @DisplayName("Member와 Image 데이터를 삽입한다.")
//    @Test
//    void initDummyAndBatchInsert() {
//        int memberCount = 100_000;
//        int imageCount = 1_000_000;
//
//        List<Member> members = generateRandomMembers(memberCount);
//        memberRepository.saveAll(members);
//        memberRepository.flush();
//
//        List<Image> images = generateRandomImagesForMembers(members, imageCount);
//        imageRepository.saveAll(images);
//        imageRepository.flush();
//    }
//
//    private List<Member> generateRandomMembers(int count) {
//        return IntStream.range(0, count)
//                .mapToObj(i -> Member.builder()
//                        .memberId(null)
//                        .status(MemberStatus.values()[new Random().nextInt(MemberStatus.values().length)])
//                        .build())
//                .collect(Collectors.toList());
//    }
//
//    private List<Image> generateRandomImagesForMembers(List<Member> members, int count) {
//        Random random = new Random();
//        return IntStream.range(0, count)
//                .mapToObj(i -> {
//                    Member member = members.get(random.nextInt(members.size()));
//                    return Image.builder()
//                            .id(null)
//                            .imageUrl(BASE_IMAGE_URL + "/" + random.nextInt(10000) + IMAGE_TYPE)
//                            .member(member)
//                            .build();
//                })
//                .collect(Collectors.toList());
//    }
//}
