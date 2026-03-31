package com.yumyum.sns.notification.entity;

import com.yumyum.sns.common.BaseTimeEntity;
import com.yumyum.sns.member.entity.Member;
import com.yumyum.sns.notification.NotificationType;
import com.yumyum.sns.notification.TargetType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Notification extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id", nullable = false)
    private Member receiver;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", nullable = false)
    private Member sender;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private NotificationType notificationType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private TargetType targetType;

    @Column(nullable = false)
    private Long targetId;

    // 읽음 여부
    @Column(nullable = false)
    private boolean isRead = false;

    // 읽은 시간
    private LocalDateTime readAt;

    @Column(nullable = false)
    private boolean isDeleted = false;

    public Notification(Member sender, Member receiver, NotificationType notificationType, TargetType targetType, Long targetId) {
        this.sender = sender;
        this.receiver = receiver;
        this.notificationType = notificationType;
        this.targetType = targetType;
        this.targetId = targetId;
    }

    public void markAsRead() {
        this.isRead = true;
        this.readAt = LocalDateTime.now();
    }

    public void markAsDeleted() {
        this.isDeleted = true;
    }
}
