package com.aptech.itblog.controller;

import com.aptech.itblog.collection.Notification;
import com.aptech.itblog.model.CommonResponseBody;
//import com.aptech.itblog.model.Notification;
import com.aptech.itblog.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;

import static com.aptech.itblog.common.CollectionLink.API;

@RestController
@RequestMapping(value = API)
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

    // The SimpMessagingTemplate is used to send Stomp over WebSocket messages.
    @Autowired
    private SimpMessagingTemplate messagingTemplate;


    /**
     * GET  /notifications  -> show the notifications page.
     */
    @RequestMapping("/notifications")
    public String notifications() {
        return "notifications";
    }

    /**
     * POST  /some-action  -> do an action.
     * <p>
     * After the action is performed will be notified UserA.
     */
    @RequestMapping(value = "/some-action/{target}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> someAction(@PathVariable String target, @RequestParam String message) {
//    public ResponseEntity<?> someAction(@RequestBody String target) {

        // Do an action here
        // ...

//        Notification notification = new Notification(message);


        // Send the notification to "UserA" (by username)
//        notificationService.notify(
//                notification, // notification object
//                target                    // username
//        );

//        messagingTemplate.convertAndSendToUser(
//                target,
//                "/queue/notify",
//                notification
//        );

        // Return an http 200 status code
//        return new ResponseEntity<>(notification.getContent(), HttpStatus.OK);
        return new ResponseEntity<>("", HttpStatus.OK);
    }

    @GetMapping(value = "/notifications")
    @ResponseBody
    public ResponseEntity<?> getTop10() {
        List<Notification> notifications = notificationService.getTop10();

        return new ResponseEntity(new CommonResponseBody("OK", 200, new LinkedHashMap() {
            {
                put("data", notifications);
            }
        }), HttpStatus.OK);
    }
}

