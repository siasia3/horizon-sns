package com.yumyum.sns.friend.entity;

import com.yumyum.sns.common.BaseTimeEntity;
import com.yumyum.sns.friend.dto.FriendResDto;
import com.yumyum.sns.member.entity.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@SqlResultSetMapping(
        name = "FriendResponseMapping",
        classes = @ConstructorResult(
                targetClass = FriendResDto.class,
                columns = {
                        @ColumnResult(name = "id", type = Long.class),
                        @ColumnResult(name = "friendId", type = Long.class),
                        @ColumnResult(name = "friendNickname", type = String.class),
                        @ColumnResult(name = "friendProfileImage", type = String.class)
                }
        )
)
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Friend extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_a_id")
    private Member memberA;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_b_id")
    private Member memberB;

    public Friend( Member memberA, Member memberB) {
        this.memberA = memberA;
        this.memberB = memberB;
    }
}

