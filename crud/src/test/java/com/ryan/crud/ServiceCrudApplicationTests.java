package com.ryan.crud;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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

    /**
     * 性能分析插件
     */
    @Test
    void  performanceAnalysis() {
        userMapper.selectList(null);
    }

    /**
     * QueryWrapper条件匹配
     *
     */
    @Test
    void QueryWrapperTest() {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();

        /**
         * 单一条件匹配
         */
        //匹配名字为Billie的记录
//        userQueryWrapper.eq("name", "Billie");
//        User user = userMapper.selectOne(userQueryWrapper);
//        log.info(user.toString());


        /**
         * 多条件查询
         */
        //查询条件：年纪在24~200之间，并且名字不为空
//        userQueryWrapper.between("age",24, 200).isNotNull("name");
//        userMapper.selectList(userQueryWrapper).forEach(System.out::println);


        /**
         * 根据条件进行统计
         */
        //统计年纪大于24的总的记录数
//        userQueryWrapper.gt("age", 24);
//        Integer integer = userMapper.selectCount(userQueryWrapper);
//        log.info("满足条件的数目为：" + integer);


        /**
         * 模糊查询
         */
        //查询名字字段里面含有字母o的记录
//        userQueryWrapper.like("name", "o");
//        userMapper.selectList(userQueryWrapper).forEach(System.out::println);


        /**
         * SQL拼接
         */
        //查询年纪大于24的所有记录
        userQueryWrapper.inSql("age", "select age from user where age > 24");
        userMapper.selectList(userQueryWrapper).forEach(System.out::println);

    }
}
