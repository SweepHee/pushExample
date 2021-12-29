package com.example.demo.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface UserService {

    public List<Map<String, Object>> getPushTokens(HashMap<String,String> params);

}
