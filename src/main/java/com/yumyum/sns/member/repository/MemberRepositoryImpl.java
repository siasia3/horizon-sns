package com.yumyum.sns.member.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yumyum.sns.friend.entity.QFriend;
import com.yumyum.sns.member.dto.MemberSearchDto;
import com.yumyum.sns.member.dto.QMemberSearchDto;
import com.yumyum.sns.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.yumyum.sns.friend.entity.QFriend.*;
import static com.yumyum.sns.member.entity.QMember.*;
import static com.yumyum.sns.post.entity.QPost.*;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom{

    private final JPAQueryFactory queryFactory;


    @Override
    public Optional<MemberSearchDto> findMemberSearch(String nickName) {

        QFriend friendA = new QFriend("friendA");
        QFriend friendB = new QFriend("friendB");

        MemberSearchDto memberInfo = queryFactory
                .select(new QMemberSearchDto(
                        member.id,
                        member.nickname,
                        member.profileImage,
                        friendA.id.countDistinct().add(friendB.id.countDistinct()),
                        post.id.countDistinct()
                ))
                .from(member)
                .leftJoin(member.postList, post)
                .leftJoin(member.friendsA, friendA)
                .leftJoin(member.friendsB, friendB)
                .where(member.nickname.eq(nickName))
                .groupBy(member.id)
                .fetchOne();
        return Optional.ofNullable(memberInfo);

    }

    @Override
    public List<Member> getPreviewByNickname(String keyword) {
        return queryFactory
                .selectFrom(member)
                .where(member.nickname.startsWithIgnoreCase(keyword))
                .orderBy(member.nickname.asc())
                .limit(3)
                .fetch();
    }
}
