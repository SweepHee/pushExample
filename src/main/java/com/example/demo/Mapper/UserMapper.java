package com.example.demo.Mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper {

    public List<Map<String, Object>> getPushTokens(HashMap<String,String> params);

}
