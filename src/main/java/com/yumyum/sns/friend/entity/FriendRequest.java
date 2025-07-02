package com.yumyum.sns.friend.entity;

import com.yumyum.sns.common.BaseTimeEntity;
import com.yumyum.sns.member.entity.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class FriendRequest extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "request_id")
    private Member requester;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    private Member receiver;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private FriendRequestStatus state;

    public FriendRequest(Member requester, Member receiver) {
        this.requester = requester;
        this.receiver = receiver;
        this.state = FriendRequestStatus.REQUESTED;
    }

    //상태값 변경
    public void changeFriendRequestState(FriendRequestStatus friendRequestStatus){
        this.state = friendRequestStatus;
    }

}
