package org.example.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import lombok.extern.slf4j.Slf4j;
import org.example.domain.User;
import org.example.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/* @ProjectName:    demo
 * @Package:        com.example.demo.server.Impl
 * @ClassName:      UserService
 * @author:     jiangtao
 * @description:
 * @date:    2022/4/20 16:04
 * @version:    1.0
 */
@Slf4j
@Service
public class UserServiceImpl {
    @Autowired
    private UserMapper userMapper;


    public List<User> findAll(){

        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();

        List<User> users = userMapper.selectList(queryWrapper);
        log.info(users.toString());
        return users;
    }
    //分页
    public List<User> findPage(int current,int pageSize){

        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        Page page = new Page(current,pageSize);
        Page<User> userPage = userMapper.selectPage(page,queryWrapper);
        log.info("total:{}",userPage.getTotal());
        log.info("pages:{}",userPage.getPages());
        return userPage.getRecords();
    }

}
