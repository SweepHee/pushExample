package com.example.demo.Service;

import com.google.firebase.messaging.FirebaseMessagingException;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface PushService {

    public void send(HashMap<String, String> params, List<Map<String, Object>> usertokens)
            throws FirebaseMessagingException, IOException;

}
