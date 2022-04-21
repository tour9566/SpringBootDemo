package org.example.service.Impl;

import com.alibaba.fastjson.JSON;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.example.domain.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* @ProjectName:    demo
 * @Package:        com.example.demo.service.Impl
 * @ClassName:      UserServiceImpl
 * @author:     jiangtao
 * @description:
 * @date:    2022/4/20 18:27
 * @version:    1.0
 */
@Slf4j
@Service
public class UserServiceImpl {
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    public List<User> findAll(){
        String userListJsonStr = redisTemplate.opsForValue().get("UserService.findAll");
        if (StringUtils.isNotBlank(userListJsonStr)){
            List<User> users = JSON.parseArray(userListJsonStr, User.class);
            log.info("走了缓存~~~");
            return users;
        }else {
            //查询所有
            User user = new User();
            user.setId(1);
            user.setName("tom");
            List<User> users = new ArrayList<User>();
            users.add(user);
            redisTemplate.opsForValue().set("UserService.findAll", JSON.toJSONString(users),2, TimeUnit.HOURS);
            log.info("存入缓存~~~");
            return users;
        }
    }

}
