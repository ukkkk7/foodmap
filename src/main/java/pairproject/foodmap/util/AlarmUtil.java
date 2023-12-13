package pairproject.foodmap.util;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import org.springframework.stereotype.Component;

@Component
public class AlarmUtil {

    public void send(String targetUserId, String title) {
        Message message = Message.builder()
                .putData("title", title)
                .setToken("DEVICE_FCM_TOKEN") // 대상 디바이스의 FCM 토큰 (프론트 단 구현 필요로 보류)
                .putData("userId", targetUserId) // 알림과 관련된 추가 데이터 전송 (옵션)
                .build();

        try {
            String response = FirebaseMessaging.getInstance().send(message);
            System.out.println("Successfully sent message: " + response);
        } catch (Exception e) {
            System.err.println("Error sending message: " + e.getMessage());
        }
    }
}
