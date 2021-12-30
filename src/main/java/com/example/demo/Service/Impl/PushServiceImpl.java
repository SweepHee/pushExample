package com.example.demo.Service.Impl;

import com.example.demo.Service.PushService;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@Configuration
@EnableAsync
public class PushServiceImpl implements PushService {



    /*
    * params = [title=푸시제목, body=푸시내용, ...]
    * pushTokens = [fcmToken=asdfefefesfsef..., ...]
    **/
    @Override
    @Async
    public void send(HashMap<String, String> params, List<Map<String, Object>> pushTokens)
            throws FirebaseMessagingException, IOException {

        /* 로컬용 */
//        FileInputStream refreshToken = new FileInputStream("/Users/seungheejeon/Desktop/workspace/2021_09/itda_real 2/src/main/resources/firebase/test-firebase-adminsdk-fv7cy-5cd6f955bf.json");

        /* 서버용 */
        FileInputStream refreshToken = new FileInputStream("/var/upload/firebase/test-firebase-adminsdk-fv7cy-5cd6f955bf.json");

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(refreshToken))
                .build();

        if(FirebaseApp.getApps().isEmpty()) {
            FirebaseApp.initializeApp(options);
        }

        int tokenCount = 0;
        int failCount = 0;

        List<String> tokenList = new ArrayList<>();

        LocalTime localTime1 = LocalTime.now();
        System.out.println("보내기시작시간:: " + localTime1);


        for (int i=0; i < pushTokens.size(); i++) {

            /* 유저의 푸시토큰을 배열에 담는다 */
            tokenList.add(pushTokens.get(i).get("pushtoken").toString());
            tokenCount++;

            /* 배열에 푸시토큰 500개 담기면 푸시보내고 배열 초기화 */
            if ((i % 499) == 0 && (i != 0)) {

                MulticastMessage message = MulticastMessage.builder()
                        .setNotification(Notification.builder()
                                .setTitle(params.get("title"))
                                .setBody(params.get("body"))
                                .build())
                        .setApnsConfig(ApnsConfig.builder() /* 아이폰 개별 설정 */
                                .setAps(Aps.builder()
                                        .setBadge(42)
                                        .setSound("default")
                                        .build())
                                .build())
                        .putData("keyId", params.get("keyId")) /* 개별파라미터 */
                        .putData("idx", params.get("idx"))
                        .putData("click_action", "FLUTTER_NOTIFICATION_CLICK")
                        .addAllTokens(tokenList)
                        .build();

                tokenList.clear();
                BatchResponse response = FirebaseMessaging.getInstance().sendMulticast(message);

                /* 실패갯수 담기 */
//                if(response.getFailureCount() > 0) {
//                    failCount += response.getFailureCount();
//                }

            }
        }

        /* 렝스500안되는 남은 토큰도 보내기 */
        if (tokenList.size() > 0) {

            MulticastMessage message = MulticastMessage.builder()
                    .setNotification(Notification.builder()
                            .setTitle(params.get("title"))
                            .setBody(params.get("body"))
                            .build())
                    .setApnsConfig(ApnsConfig.builder()
                            .setAps(Aps.builder()
                                    .setBadge(42)
                                    .setSound("default")
                                    .build())
                            .build())
                    .putData("keyId", params.get("keyId"))
                    .putData("idx", params.get("idx"))
                    .putData("click_action", "FLUTTER_NOTIFICATION_CLICK")
                    .addAllTokens(tokenList)
                    .build();

            BatchResponse response = FirebaseMessaging.getInstance().sendMulticast(message);

            /* 실패갯수 담기 */
//                if(response.getFailureCount() > 0) {
//                    failCount += response.getFailureCount();
//                }
        }


        LocalTime localTime2 = LocalTime.now();
        System.out.println("보내기시작시간 :: " + localTime1 +", 보내기종료시간 :: " + localTime2);

    }

}
