package com.yumyum.sns.notification.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yumyum.sns.notification.entity.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import static com.yumyum.sns.member.entity.QMember.member;
import static com.yumyum.sns.notification.entity.QNotification.notification;

@Repository
@RequiredArgsConstructor
public class NotificationRepositoryImpl implements NotificationCustom{

    private final JPAQueryFactory queryFactory;

    //알림 목록 조회
    @Override
    public Slice<Notification> findNotificationsWithCursor(Long receiverId, Long lastId, int size) {
        List<Notification> notificationList = queryFactory
                .selectFrom(notification)
                .join(notification.sender, member).fetchJoin()
                .where(receiverIdEq(receiverId),lastIdlt(lastId),isDeleted())
                .orderBy(notification.id.desc())
                .limit(size + 1)
                .fetch();

        boolean hasNext = notificationList.size() > size;
        if (hasNext) notificationList.remove(size);

        return new SliceImpl<>(notificationList, PageRequest.of(0, size),hasNext);
    }

    //읽지 않은 목록 갯수
    @Override
    public Long countUnreadNotifications(Long receiverId) {
        Long unreadNotificationCnt = queryFactory.select(notification.count())
                .from(notification)
                .where(receiverIdEq(receiverId),isRead(),isDeleted())
                .fetchOne();

        return unreadNotificationCnt != null ? unreadNotificationCnt : 0L;
    }

    //알림 전부 삭제 상태로 변경
    @Override
    public void deleteAllNotifications(Long receiverId) {
        queryFactory.update(notification)
                .set(notification.isDeleted, true)
                .where(receiverIdEq(receiverId), isDeleted())
                .execute();
    }

    //알림 전부 읽음 상태로 변경
    @Override
    public void markAllAsRead(Long receiverId) {
        queryFactory.update(notification)
                .set(notification.isRead, true)
                .set(notification.readAt, LocalDateTime.now())
                .where(
                    receiverIdEq(receiverId),isRead(),isDeleted()
                )
                .execute();
    }

    private BooleanExpression lastIdlt(Long lastId){
        return lastId != null ? notification.id.lt(lastId) : null;
    }

    private BooleanExpression isDeleted(){
        return notification.isDeleted.eq(false);
    }

    private BooleanExpression isRead(){
        return notification.isRead.eq(false);
    }

    private BooleanExpression receiverIdEq(Long receiverId){
        return notification.receiver.id.eq(receiverId);
    }
}
