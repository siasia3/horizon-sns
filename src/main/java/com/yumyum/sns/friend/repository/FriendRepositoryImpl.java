package com.yumyum.sns.friend.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yumyum.sns.friend.dto.FriendResDto;
import com.yumyum.sns.friend.entity.QFriend;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.yumyum.sns.friend.entity.QFriend.*;

@Repository
@RequiredArgsConstructor
public class FriendRepositoryImpl implements FriendRepositoryCustom{

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    @Override
    public List<FriendResDto> findFriendsBymemberId(Long myId) {
        String sql = """
            SELECT
                f.id            AS id,
                m.id            AS friendId,
                m.nickname      AS friendNickname,
                m.profile_image AS friendProfileImage
            FROM friend f
            JOIN member m ON f.member_b_id = m.id
            WHERE f.member_a_id = ?

            UNION ALL

            SELECT
                f.id            AS id,
                m.id            AS friendId,
                m.nickname      AS friendNickname,
                m.profile_image AS friendProfileImage
            FROM friend f
            JOIN member m ON f.member_a_id = m.id
            WHERE f.member_b_id = ?
            """;

        return em.createNativeQuery(sql, "FriendResponseMapping")
                .setParameter(1, myId)
                .setParameter(2, myId)
                .getResultList();
    }

    @Override
    public void deleteFriend(Long senderId, Long receiverId) {
        queryFactory
                .delete(friend)
                .where(
                    friend.memberA.id.eq(senderId)
                    .and(friend.memberB.id.eq(receiverId))
                )
                .execute();
    }


}
