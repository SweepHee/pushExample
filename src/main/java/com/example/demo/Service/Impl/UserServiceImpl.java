package com.example.demo.Service.Impl;

import com.example.demo.Mapper.UserMapper;
import com.example.demo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public List<Map<String, Object>> getPushTokens(HashMap<String,String> params) {
        return userMapper.getPushTokens(params);
    }

}
