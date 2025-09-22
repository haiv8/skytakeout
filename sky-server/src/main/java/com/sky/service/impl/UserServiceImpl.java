package com.sky.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sky.constant.JwtClaimsConstant;
import com.sky.constant.MessageConstant;
import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;
import com.sky.exception.LoginFailedException;
import com.sky.mapper.UserMapper;
import com.sky.properties.JwtProperties;
import com.sky.properties.WeChatProperties;
import com.sky.service.UserService;
import com.sky.utils.HttpClientUtil;
import com.sky.utils.JwtUtil;
import com.sky.vo.EmployeeLoginVO;
import com.sky.vo.UserLoginVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private WeChatProperties weChatProperties;
    @Autowired
    private UserMapper userMapper;

    @Override
    public User login(UserLoginDTO dto) {


        HashMap<String, String> paramMap = new HashMap<>();
        paramMap.put("appid",weChatProperties.getAppid());
        paramMap.put("secret",weChatProperties.getSecret());
        paramMap.put("js_code", dto.getCode());
        paramMap.put("grant_type", "authorization_code");
        String res = HttpClientUtil.doGet("https://api.weixin.qq.com/sns/jscode2session", paramMap);
        log.info("res={}",res);
        JSONObject jsonObject = JSON.parseObject(res);
        String openid = (String) jsonObject.get("openid");
        if (openid ==null){
            throw new LoginFailedException(MessageConstant.USER_NOT_LOGIN);
        }
        User user = userMapper.selectByOpenid(openid);
        if(user == null){
            user = new User();
            user.setOpenid(openid);
            user.setCreateTime(LocalDateTime.now());
            user.setName(openid.substring(0,5));
            userMapper.insert(user);
        }

        return user;
    }
}
