package com.ryan.crud;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ryan.crud.entity.User;
import com.ryan.crud.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
//        User user = new User();
//        user.setId("783a264a1f79554e31b8a6edee5ef996");
//        user.setName("nan");
//        user.setEmail("321999899883@qq.com");
//        user.setAge(38);
//        userMapper.updateById(user);
    }

    @Test
    void OptimisticLockerSuccessTest() {
        //乐观锁成功
        User user = userMapper.selectById(1);
        log.info("更新前：" + user.toString());
        user.setAge(100);
        user.setName("xiaofan");
        userMapper.updateById(user);
        log.info("更新后：" + user.toString());

    }

    @Test
    void OptimisticLockerFailTest() {
        //乐观锁失败
        User user = userMapper.selectById(1);
        log.info("更新前：" + user.toString());
        user.setAge(180);
        user.setName("xiaofan");

        //模拟线程插队操作
        User user2 = userMapper.selectById(1);
        user2.setAge(60);
        user2.setName("插队");

        userMapper.updateById(user);
        log.info("更新后：" + user.toString());

    }

    /**
     * 查询操作
     */
    @Test
    void selectOperationTest() {

        //根据id进行查询
        User user = userMapper.selectById(1);
        log.info(user.toString());

        //根据一组id进行查询
        log.info("-----------------一组ids查询----------------");
        List<User> users = userMapper.selectBatchIds(Arrays.asList(1, 2, 3, 4));
        users.forEach(System.out::println);

        //根据条件查询
        log.info("-----------------条件查询----------------");
        Map<String, Object> conditionMap = new HashMap<>();
        conditionMap.put("name", "xiaofan");
        List<User> users1 = userMapper.selectByMap(conditionMap);
        users1.forEach(System.out::println);
    }

    /**
     * 分页查询
     */
    @Test
    void pageTest() {
        Page<User> page = new Page<>(1, 2);
        Page<User> userPage = userMapper.selectPage(page, null);
        userPage.getRecords().forEach(System.out::println);
        log.info("记录总数：" + page.getTotal());

    }

    /**
     * 删除操作
     */
    @Test
    void deleteTest() {
        int delete = userMapper.delete(null);
        int i = userMapper.deleteById(1);
        int i1 = userMapper.deleteBatchIds(Arrays.asList(1, 2, 3, 4));
        Map<String, Object> conditionMap = new HashMap<>();
        conditionMap.put("name", "xiaofan");
        userMapper.deleteByMap(conditionMap);
    }

    /**
     * 逻辑删除
     */
    @Test
    void logicDeleteTest() {
        userMapper.deleteBatchIds(Arrays.asList(1, 2, 3, 4));
        userMapper.selectList(null).forEach(System.out::println);
    }
}
