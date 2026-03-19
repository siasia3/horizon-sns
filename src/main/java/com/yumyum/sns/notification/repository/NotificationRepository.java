package com.yumyum.sns.notification.repository;

import com.yumyum.sns.notification.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification,Long>,NotificationCustom {
}
