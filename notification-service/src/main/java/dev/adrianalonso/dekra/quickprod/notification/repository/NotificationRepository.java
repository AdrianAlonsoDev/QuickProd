package dev.adrianalonso.dekra.quickprod.notification.repository;

import dev.adrianalonso.dekra.quickprod.notification.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {
}
