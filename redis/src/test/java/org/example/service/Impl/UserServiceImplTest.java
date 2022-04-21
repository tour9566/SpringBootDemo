package org.example.service.Impl;

import org.example.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class UserServiceImplTest {

    @Autowired
    UserServiceImpl userService ;

    @Test
    void findAll() {
        List<User> all = userService.findAll();
        System.out.println(all);

    }
}