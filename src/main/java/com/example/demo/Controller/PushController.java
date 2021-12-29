package com.example.demo.Controller;

import com.example.demo.Service.PushService;
import com.example.demo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class PushController {


    @Autowired
    UserService userService;

    @Autowired
    PushService pushService;


    @PostMapping(value="/send")
    @ResponseBody
    public String index(@RequestParam HashMap<String,String> params) throws Exception {

        /* 푸시토큰 가져오기
         * [{pushtoken='tokenstring}, {pushtoken='tokenstring'}...]
         * */
        List<Map<String, Object>> pushTokens = userService.getPushTokens(params);

        pushService.send(params, pushTokens);
        return "성공";

    }

}
