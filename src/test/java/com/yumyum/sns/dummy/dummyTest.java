package com.yumyum.sns.dummy;

import com.yumyum.sns.attachment.entity.Attachment;
import com.yumyum.sns.attachment.entity.AttachmentDetail;
import com.yumyum.sns.attachment.repository.AttachmentDetailRepository;
import com.yumyum.sns.attachment.repository.AttachmentRepository;
import com.yumyum.sns.friend.entity.Friend;
import com.yumyum.sns.friend.repository.FriendRepository;
import com.yumyum.sns.member.entity.Member;
import com.yumyum.sns.member.repository.MemberRepository;
import com.yumyum.sns.post.entity.Post;
import net.datafaker.Faker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.*;

@SpringBootTest
public class dummyTest {

    Faker faker = new Faker(new Locale("ko","ko"));
    private final Random random = new Random();
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private AttachmentRepository attachmentRepository;
    @Autowired
    private AttachmentDetailRepository attachmentDetailRepository;
    @Autowired
    private FriendRepository friendRepository;


    @Test
    void dummyMember() {
        final int TOTAL = 100_000;
        final int BATCH_SIZE = 1_000;

        List<Member> batch = new ArrayList<>(BATCH_SIZE);

        for (int i = 0; i < TOTAL; i++) {
            // 랜덤 더미 생성
            String name       = faker.name().fullName().replaceAll(" ", "");
            String phone      = faker.numerify("010########");
            String nickname   = "냠냠"+i;
            String gender     = random.nextBoolean() ? "M" : "F";
            LocalDate birthday= faker.timeAndDate().birthday();
            String role       = "ROLE_USER";
            String provider   = "local";
            String providerId = UUID.randomUUID().toString().replaceAll("-", "");
            String identifier = provider + " " + providerId;

            batch.add(new Member(name, phone, birthday, nickname, gender, identifier, role));

            // 배치 사이즈만큼 모이면 저장 후 비우기
            if (batch.size() == BATCH_SIZE) {
                memberRepository.saveAll(batch);
                memberRepository.flush();  // JpaRepository에 flush() 메소드가 있다면 사용
                batch.clear();
            }
        }

        // 남은 배치 처리
        if (!batch.isEmpty()) {
            memberRepository.saveAll(batch);
            memberRepository.flush();
            batch.clear();
        }
    }

    void dummyAttach(){



    }

    void dummyPost(List<Member> members){
        List<Attachment> attachments = new ArrayList<>();
        List<AttachmentDetail> attachmentDetails = new ArrayList<>();

        for (int i = 0; i < 10000; i++) {
            Attachment attachment = new Attachment();

            AttachmentDetail attachmentDetail = AttachmentDetail.builder()
                    .attachment(attachment)
                    .size(105707L)
                    .originalFileName("배경.jfif")
                    .path("https://my-demo-krm.s3.amazonaws.com/6f348182-1903-4b93-b0c5-69b25e83ff92_%EB%B0%B0%EA%B2%BD.jfif")
                    .savedFileName("6f348182-1903-4b93-b0c5-69b25e83ff92_배경.jfif")
                    .type("image/jpeg")
                    .build();

            attachments.add(attachment);
            attachmentDetails.add(attachmentDetail);



            String content = "오늘은 날씨가 너무 좋아서 근처 공원 한 바퀴 돌았는데 기분 전환 제대로 됐어요. 이런 날은 진짜 집에만 있기 아깝다!\n" +
                    "\n" +
                    "최근에 본 영화가 생각보다 괜찮았어요. 기대 안 했는데 몰입감 있고 여운도 길게 남네요.\n" +
                    "\n" +
                    "하루 종일 비 오더니 저녁엔 맑아졌어요. 비 오는 날엔 괜히 감성적으로 변하는 느낌…";

            Post post = new Post(content, attachmentDetail.getPath());
            //post.setPostRelation(members.get(i), savedAttach);
        }
    }

    @Test
    void dummyFriend(){
        List<Friend> friends = new ArrayList<>();

        for (int i = 0; i < 10000; i++) {
            if(i%2==0){
                Member memberA = new Member(1L);
                Long id = Long.valueOf(i+2);
                Member memberB = new Member(id);

                Friend friend = new Friend(memberA,memberB);
                friends.add(friend);
            }else{
                Long id = Long.valueOf(i+2);
                Member memberA = new Member(id);
                Member memberB = new Member(1L);

                Friend friend = new Friend(memberA,memberB);
                friends.add(friend);
            }
        }

        friendRepository.saveAll(friends);
    }
}
