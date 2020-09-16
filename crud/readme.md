# CURD接口

#### service CURD

#### Mapper CURD

- insert操作

    ```java
    package com.ryan.crud;
    
    import com.ryan.crud.mapper.UserMapper;
    import entity.User;
    import lombok.extern.slf4j.Slf4j;
    import org.junit.jupiter.api.Test;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.boot.test.context.SpringBootTest;
    
    import javax.annotation.Resource;
    
    @Slf4j
    @SpringBootTest
    class ServiceCrudApplicationTests {
    
        @Resource
        private UserMapper userMapper;
    
        @Test
        void contextLoads() {
    
            int ryan = userMapper.insert(new User(6,"Ryan", 6, "1312170600@qq.com"));
            log.info(ryan+"");
        }
    
    }
    ```
  
  - insert引申扩展
  
    1. 主键策略
    
        注解@TableId
        
        - IdType.AUTO 
             
            数据库ID自增
            
            ```java
               //测试主键策略AUTO
               User user = new User();
               user.setName("fuyi");
               user.setEmail("32894899@qq.com");
               user.setAge(18);
               int ryan = userMapper.insert(user);
               log.info(ryan+"");
           ```
           测试结果
           
           ```text
            Creating a new SqlSession
            SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@ef60710] was not registered for synchronization because synchronization is not active
            2020-09-16 11:18:28.947  INFO 3392 --- [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Starting...
            2020-09-16 11:18:29.303  INFO 3392 --- [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Start completed.
            JDBC Connection [HikariProxyConnection@1664927413 wrapping com.mysql.jdbc.JDBC4Connection@6342ff7f] will not be managed by Spring
            ==>  Preparing: INSERT INTO user ( name, age, email ) VALUES ( ?, ?, ? )
            ==> Parameters: fuyi(String), 18(Integer), 32894899@qq.com(String)
            <==    Updates: 1
            Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@ef60710]
            ```
             
        **注意**：id字段在数据表中必须设置称自动增加不然，无法插入数据。
        
        - IdType.NONE      
        
            无状态,该类型为未设置主键类型(注解里等于跟随全局,全局里约等于 INPUT)
        
        - IdType.INPUT     
        
            insert前自行set主键值
        
        - IdType.ASSIGN_ID 
        
            分配ID(主键类型为Number(Long和Integer)或String)(since 3.3.0),使用接口IdentifierGenerator的方法nextId(默认实现类为DefaultIdentifierGenerator雪花算法)
            
            实体类
          ```java
              package entity;
              
              import com.baomidou.mybatisplus.annotation.IdType;
              import com.baomidou.mybatisplus.annotation.TableId;
              import com.baomidou.mybatisplus.core.mapper.BaseMapper;
              import lombok.AllArgsConstructor;
              import lombok.Data;
              import lombok.EqualsAndHashCode;
              import lombok.NoArgsConstructor;
              
              import static com.baomidou.mybatisplus.annotation.IdType.*;
              
              /**
               * <p>
               *
               * </p>
               *
               * @author fuyi
               * @since 2020-09-15
               */
              @Data
              @EqualsAndHashCode()
              @AllArgsConstructor
              @NoArgsConstructor
              public class User{
              
                  @TableId(type = ASSIGN_ID)
                  private Long id;
                  /**
                   * 姓名
                   */
                  private String name;
              
                  /**
                   * 年龄
                   */
                  private Integer age;
              
                  /**
                   * 邮箱
                   */
                  private String email;
              
              
              }

             ```
          
            测试代码
            ```java
          //测试主键策略ASSIGN_ID
          User user = new User();
          user.setName("xiaobai");
          user.setEmail("32894899883@qq.com");
          user.setAge(20);
          int ryan = userMapper.insert(user);
             ```
          
            测试结果
            ```text
                Creating a new SqlSession
                SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@2503ec73] was not registered for synchronization because synchronization is not active
                2020-09-16 11:26:22.691  INFO 6820 --- [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Starting...
                2020-09-16 11:26:23.055  INFO 6820 --- [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Start completed.
                JDBC Connection [HikariProxyConnection@1416977881 wrapping com.mysql.jdbc.JDBC4Connection@f1f7db2] will not be managed by Spring
                ==>  Preparing: INSERT INTO user ( id, name, age, email ) VALUES ( ?, ?, ?, ? )
                ==> Parameters: 1306071905983332353(Long), xiaobai(String), 20(Integer), 32894899883@qq.com(String)
                <==    Updates: 1
                Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@2503ec73]
             ```
        - IdType.ASSIGN_UUID 
        
            分配UUID,主键类型为String(since 3.3.0),使用接口IdentifierGenerator的方法nextUUID(默认default方法)
            
            实体类
            ```java
                @TableId(type = ASSIGN_UUID)
                private String id;
                /**
                 * 姓名
                 */
                private String name;
            
                /**
                 * 年龄
                 */
                private Integer age;
            
                /**
                 * 邮箱
                 */
                private String email;
             ```   
            
            测试代码
            
            ```java
                //测试主键策略ASSIGN_UUID
                User user = new User();
                user.setName("hei");
                user.setEmail("321999899883@qq.com");
                user.setAge(40);
                userMapper.insert(user);
             ```                              
            测试结果
            
            ```text
            Creating a new SqlSession
            SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@65ddee5a] was not registered for synchronization because synchronization is not active
            2020-09-16 11:34:33.696  INFO 9376 --- [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Starting...
            2020-09-16 11:34:34.059  INFO 9376 --- [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Start completed.
            JDBC Connection [HikariProxyConnection@1202790087 wrapping com.mysql.jdbc.JDBC4Connection@58fa5769] will not be managed by Spring
            ==>  Preparing: INSERT INTO user ( id, name, age, email ) VALUES ( ?, ?, ?, ? )
            ==> Parameters: 783a264a1f79554e31b8a6edee5ef996(String), hei(String), 40(Integer), 321999899883@qq.com(String)
            <==    Updates: 1
            Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@65ddee5a]
            ```
            
        - IdType.ID_WORKER
        
            分布式全局唯一ID 长整型类型(please use ASSIGN_ID)
            
        - IdType.ID_UUID
        
            32位UUID字符串(please use ASSIGN_UUID)
            
        - IdType.ID_WORKER_STR
        
            分布式全局唯一ID 字符串类型(please use ASSIGN_ID）
            

- select操作

    ```java
        //获取全部
        List<User> users = userMapper.selectList(null);
        for (User u: users) {
            log.info(u.toString());
        }
    ```
    测试结果
    
    ```text
    Creating a new SqlSession
    SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@44b21f9f] was not registered for synchronization because synchronization is not active
    2020-09-16 11:39:42.102  INFO 7132 --- [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Starting...
    2020-09-16 11:39:42.468  INFO 7132 --- [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Start completed.
    JDBC Connection [HikariProxyConnection@1874919946 wrapping com.mysql.jdbc.JDBC4Connection@5762658b] will not be managed by Spring
    ==>  Preparing: SELECT id,name,age,email FROM user
    ==> Parameters: 
    <==    Columns: id, name, age, email
    <==        Row: 1, Jone, 18, test1@baomidou.com
    <==        Row: 1306071905983332353, xiaobai, 20, 32894899883@qq.com
    <==        Row: 2, Jack, 20, test2@baomidou.com
    <==        Row: 3, Tom, 28, test3@baomidou.com
    <==        Row: 4, Sandy, 21, test4@baomidou.com
    <==        Row: 5, Billie, 24, test5@baomidou.com
    <==        Row: 6, Ryan, 6, 1312170600@qq.com
    <==        Row: 7, fuyi, 18, 32894899@qq.com
    <==        Row: 783a264a1f79554e31b8a6edee5ef996, hei, 40, 321999899883@qq.com
    <==      Total: 9
    Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@44b21f9f]
    2020-09-16 11:39:42.514  INFO 7132 --- [           main] c.ryan.crud.ServiceCrudApplicationTests  : User(id=1, name=Jone, age=18, email=test1@baomidou.com)
    2020-09-16 11:39:42.514  INFO 7132 --- [           main] c.ryan.crud.ServiceCrudApplicationTests  : User(id=1306071905983332353, name=xiaobai, age=20, email=32894899883@qq.com)
    2020-09-16 11:39:42.514  INFO 7132 --- [           main] c.ryan.crud.ServiceCrudApplicationTests  : User(id=2, name=Jack, age=20, email=test2@baomidou.com)
    2020-09-16 11:39:42.514  INFO 7132 --- [           main] c.ryan.crud.ServiceCrudApplicationTests  : User(id=3, name=Tom, age=28, email=test3@baomidou.com)
    2020-09-16 11:39:42.514  INFO 7132 --- [           main] c.ryan.crud.ServiceCrudApplicationTests  : User(id=4, name=Sandy, age=21, email=test4@baomidou.com)
    2020-09-16 11:39:42.514  INFO 7132 --- [           main] c.ryan.crud.ServiceCrudApplicationTests  : User(id=5, name=Billie, age=24, email=test5@baomidou.com)
    2020-09-16 11:39:42.514  INFO 7132 --- [           main] c.ryan.crud.ServiceCrudApplicationTests  : User(id=6, name=Ryan, age=6, email=1312170600@qq.com)
    2020-09-16 11:39:42.514  INFO 7132 --- [           main] c.ryan.crud.ServiceCrudApplicationTests  : User(id=7, name=fuyi, age=18, email=32894899@qq.com)
    2020-09-16 11:39:42.514  INFO 7132 --- [           main] c.ryan.crud.ServiceCrudApplicationTests  : User(id=783a264a1f79554e31b8a6edee5ef996, name=hei, age=40, email=321999899883@qq.com)
    ```
    
  - select操作扩展
  
    1. 条件构造器（Wrapper）
    

- update操作

    ```java
        //更新操作
        User user = new User();
        user.setId("783a264a1f79554e31b8a6edee5ef996");
        user.setName("nan");
        user.setEmail("321999899883@qq.com");
        user.setAge(38);
        userMapper.updateById(user);
    ```
  
    执行结果
    
    ```text
    Creating a new SqlSession
    SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@6a87026] was not registered for synchronization because synchronization is not active
    2020-09-16 11:47:20.495  INFO 968 --- [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Starting...
    2020-09-16 11:47:20.849  INFO 968 --- [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Start completed.
    JDBC Connection [HikariProxyConnection@2107841088 wrapping com.mysql.jdbc.JDBC4Connection@28ee7bee] will not be managed by Spring
    ==>  Preparing: UPDATE user SET name=?, age=?, email=? WHERE id=?
    ==> Parameters: nan(String), 38(Integer), 321999899883@qq.com(String), 783a264a1f79554e31b8a6edee5ef996(String)
    <==    Updates: 1
    Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@6a87026]
    ```
  
  - 扩展
    
    1. 乐观锁
    
        每次去获取数据的时候，总是认为数据不会被修改，不会上锁，只是在更新的时候通过版本号机制和和CAS算法进行判断是否有人在这期间更新这个数据。
    
    2. 悲观锁
    
        和乐观锁恰恰相反，总是认为数据会被修改，每次操作数据的时候，都会上锁，知道操作完数据才会释放资源。
        
    乐观锁插件配置
    
    - 
    
    
        
    
 

