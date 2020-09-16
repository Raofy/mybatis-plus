package com.ryan.crud;

import com.ryan.crud.mapper.UserMapper;
import entity.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@SpringBootTest
class ServiceCrudApplicationTests {

    @Resource
    private UserMapper userMapper;

    @Test
    void contextLoads() {
        //测试主键策略AUTO
//        User user = new User();
//        user.setName("fuyi");
//        user.setEmail("32894899@qq.com");
//        user.setAge(18);
//        int ryan = userMapper.insert(user);
//        log.info(ryan+"");

        //测试主键策略ASSIGN_ID
//        User user = new User();
//        user.setName("xiaobai");
//        user.setEmail("32894899883@qq.com");
//        user.setAge(20);
//        int ryan = userMapper.insert(user);
//        log.info(ryan+"");

        //测试主键策略ASSIGN_UUID
//        User user = new User();
//        user.setName("hei");
//        user.setEmail("321999899883@qq.com");
//        user.setAge(40);
//        userMapper.insert(user);


        //获取全部
//        List<User> users = userMapper.selectList(null);
//        for (User u: users) {
//            log.info(u.toString());
//        }

        //更新操作
        User user = new User();
        user.setId("783a264a1f79554e31b8a6edee5ef996");
        user.setName("nan");
        user.setEmail("321999899883@qq.com");
        user.setAge(38);
        userMapper.updateById(user);


    }

}
