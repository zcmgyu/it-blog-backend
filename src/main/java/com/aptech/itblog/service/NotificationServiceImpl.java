package com.aptech.itblog.service;

import com.aptech.itblog.collection.Notification;
import com.aptech.itblog.collection.User;
import com.aptech.itblog.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for sending notification messages.
 */
@Service
public class NotificationServiceImpl implements NotificationService {

    // The SimpMessagingTemplate is used to send Stomp over WebSocket messages.
    @Autowired
    SimpMessagingTemplate messagingTemplate;

    @Autowired
    NotificationRepository notificationRepository;

    /**
     * Send notification to users subscribed on channel "/user/queue/notify".
     * <p>
     * The message will be sent only to the user with the given username.
     *
     * @param notification The notification message.
     * @param username     The username for the user to send notification.
     */
//    public void notify(Notification notification, String username) {
//        messagingTemplate.convertAndSendToUser(
//                username,
//                "/queue/notify",
//                notification
//        );
//    }
    public void notify(Notification notification, String username) {
        messagingTemplate.convertAndSendToUser(
                username,
                "/queue/notify",
                notification
        );
    }

    @Override
    public void save(Notification notification) {
        notificationRepository.save(notification);
    }

    @Override
    public List<Notification> getTop10() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return notificationRepository.findTop10ByUserIdOrderByCreateAtDesc(user.getId());
    }
}
