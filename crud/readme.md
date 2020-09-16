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
        
    乐观锁的执行方式
        
        - 取出数据时，获取当前的version
        
        - 更新的时候带上这个version
        
        - 执行更新时候，set version = new version where version = old vesion
        
        - 如果version不对，则更新失败
        
         实现原理
         
         ```sql
         -- A线程执行更新操作
         update user set name = "fuyi", version = version + 1 where id = 2 and version = 1;
         
         -- B线程也更新数据
         update user set name = "xiaohung", version = version + 1 where id = 2 and version = 1;
         ```
         虽然A线程首先开始执行更新操作，但是后面的B线程后来居上，首先完成更新操作，此时记录中的version = 2，因为A线程在执行更新操作之前首先先查询
         
         version值并记录（此时为1），当执行更新的时候，发现version已经发生变化，导致更新失败，以此来实现线程间的通信安全。
           
    Mybatis-Plus实现乐观锁
    
    1. 数据表中添加version字段，并赋值为1
    
    2. 同步实体类
    
    ```java
        package com.ryan.crud.entity;
        
        import com.baomidou.mybatisplus.annotation.Version;
        import lombok.AllArgsConstructor;
        import lombok.Data;
        import lombok.NoArgsConstructor;
        
        @Data
        @AllArgsConstructor
        @NoArgsConstructor
        public class User {
            private Long id;
            private String name;
            private Integer age;
            private String email;
        
            @Version
            private Integer version;
        }

     ```
    
    3. 配置乐观锁拦截器
    
    ```java
        package com.ryan.crud.config;
        
        import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
        import org.mybatis.spring.annotation.MapperScan;
        import org.springframework.context.annotation.Bean;
        import org.springframework.context.annotation.Configuration;
        import org.springframework.transaction.annotation.EnableTransactionManagement;
        
        @Configuration
        @EnableTransactionManagement   //开启事务管理
        @MapperScan("com.ryan.crud.mapper")
        public class MybatisPlusConfig {
        
            //乐观锁配置
            @Bean
            public OptimisticLockerInterceptor optimisticLockerInterceptor() {
                return new OptimisticLockerInterceptor();
            }
        }

     ```
    
    4. 测试
    
    ```java
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
     ```
    
    结果打印
    
    ```text
    Creating a new SqlSession
    SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@3ae126d1] was not registered for synchronization because synchronization is not active
    2020-09-16 23:07:02.877  INFO 4376 --- [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Starting...
    2020-09-16 23:07:03.339  INFO 4376 --- [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Start completed.
    JDBC Connection [HikariProxyConnection@598284487 wrapping com.mysql.jdbc.JDBC4Connection@7a45d714] will not be managed by Spring
    ==>  Preparing: SELECT id,name,age,email,version FROM user WHERE id=?
    ==> Parameters: 1(Integer)
    <==    Columns: id, name, age, email, version
    <==        Row: 1, Jone, 18, test1@baomidou.com, 1
    <==      Total: 1
    Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@3ae126d1]
    2020-09-16 23:07:03.468  INFO 4376 --- [           main] c.ryan.crud.ServiceCrudApplicationTests  : 更新前：User(id=1, name=Jone, age=18, email=test1@baomidou.com, version=1)
    Creating a new SqlSession
    SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@2f7efd0b] was not registered for synchronization because synchronization is not active
    JDBC Connection [HikariProxyConnection@295372946 wrapping com.mysql.jdbc.JDBC4Connection@7a45d714] will not be managed by Spring
    ==>  Preparing: UPDATE user SET name=?, age=?, email=?, version=? WHERE id=? AND version=?
    ==> Parameters: xiaofan(String), 100(Integer), test1@baomidou.com(String), 2(Integer), 1(Long), 1(Integer)
    <==    Updates: 1
    Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@2f7efd0b]
    2020-09-16 23:07:03.549  INFO 4376 --- [           main] c.ryan.crud.ServiceCrudApplicationTests  : 更新后：User(id=1, name=xiaofan, age=100, email=test1@baomidou.com, version=2)
    ```
    
    ```java
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
    ```
    
    结果打印
    
    ```text
    Creating a new SqlSession
    SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@4397a639] was not registered for synchronization because synchronization is not active
    2020-09-16 23:17:59.277  INFO 17776 --- [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Starting...
    2020-09-16 23:17:59.679  INFO 17776 --- [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Start completed.
    JDBC Connection [HikariProxyConnection@955743449 wrapping com.mysql.jdbc.JDBC4Connection@4a8e6e89] will not be managed by Spring
    ==>  Preparing: SELECT id,name,age,email,version FROM user WHERE id=?
    ==> Parameters: 1(Integer)
    <==    Columns: id, name, age, email, version
    <==        Row: 1, xiaofan, 100, test1@baomidou.com, 2
    <==      Total: 1
    Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@4397a639]
    2020-09-16 23:17:59.734  INFO 17776 --- [           main] c.ryan.crud.ServiceCrudApplicationTests  : 更新前：User(id=1, name=xiaofan, age=100, email=test1@baomidou.com, version=2)
    Creating a new SqlSession
    SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@42a0501e] was not registered for synchronization because synchronization is not active
    JDBC Connection [HikariProxyConnection@111819772 wrapping com.mysql.jdbc.JDBC4Connection@4a8e6e89] will not be managed by Spring
    ==>  Preparing: SELECT id,name,age,email,version FROM user WHERE id=?
    ==> Parameters: 1(Integer)
    <==    Columns: id, name, age, email, version
    <==        Row: 1, xiaofan, 100, test1@baomidou.com, 2
    <==      Total: 1
    Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@42a0501e]
    Creating a new SqlSession
    SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@2b5c4f17] was not registered for synchronization because synchronization is not active
    JDBC Connection [HikariProxyConnection@1339858954 wrapping com.mysql.jdbc.JDBC4Connection@4a8e6e89] will not be managed by Spring
    ==>  Preparing: UPDATE user SET name=?, age=?, email=?, version=? WHERE id=? AND version=?
    ==> Parameters: xiaofan(String), 180(Integer), test1@baomidou.com(String), 3(Integer), 1(Long), 2(Integer)
    <==    Updates: 1
    Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@2b5c4f17]
    2020-09-16 23:17:59.809  INFO 17776 --- [           main] c.ryan.crud.ServiceCrudApplicationTests  : 更新后：User(id=1, name=xiaofan, age=180, email=test1@baomidou.com, version=3)

     ```
    
- 查询操作

    ```java
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
    ```
  
  结果打印
  
  ```text
    JDBC Connection [HikariProxyConnection@31567969 wrapping com.mysql.jdbc.JDBC4Connection@38f77cd9] will not be managed by Spring
    ==>  Preparing: SELECT id,name,age,email,version FROM user WHERE id=?
    ==> Parameters: 1(Integer)
    <==    Columns: id, name, age, email, version
    <==        Row: 1, xiaofan, 180, test1@baomidou.com, 3
    <==      Total: 1
    Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@11c3ff67]
    2020-09-16 23:37:57.165  INFO 14292 --- [           main] c.ryan.crud.ServiceCrudApplicationTests  : User(id=1, name=xiaofan, age=180, email=test1@baomidou.com, version=3)
    2020-09-16 23:37:57.165  INFO 14292 --- [           main] c.ryan.crud.ServiceCrudApplicationTests  : -----------------一组ids查询----------------
    Creating a new SqlSession
    SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@6abdec0e] was not registered for synchronization because synchronization is not active
    JDBC Connection [HikariProxyConnection@1917555614 wrapping com.mysql.jdbc.JDBC4Connection@38f77cd9] will not be managed by Spring
    ==>  Preparing: SELECT id,name,age,email,version FROM user WHERE id IN ( ? , ? , ? , ? )
    ==> Parameters: 1(Integer), 2(Integer), 3(Integer), 4(Integer)
    <==    Columns: id, name, age, email, version
    <==        Row: 1, xiaofan, 180, test1@baomidou.com, 3
    <==        Row: 2, Jack, 20, test2@baomidou.com, 1
    <==        Row: 3, Tom, 28, test3@baomidou.com, 1
    <==        Row: 4, Sandy, 21, test4@baomidou.com, 1
    <==      Total: 4
    Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@6abdec0e]
    User(id=1, name=xiaofan, age=180, email=test1@baomidou.com, version=3)
    User(id=2, name=Jack, age=20, email=test2@baomidou.com, version=1)
    User(id=3, name=Tom, age=28, email=test3@baomidou.com, version=1)
    User(id=4, name=Sandy, age=21, email=test4@baomidou.com, version=1)
    2020-09-16 23:37:57.219  INFO 14292 --- [           main] c.ryan.crud.ServiceCrudApplicationTests  : -----------------条件查询----------------
    Creating a new SqlSession
    SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@7cca01a8] was not registered for synchronization because synchronization is not active
    JDBC Connection [HikariProxyConnection@1002762002 wrapping com.mysql.jdbc.JDBC4Connection@38f77cd9] will not be managed by Spring
    ==>  Preparing: SELECT id,name,age,email,version FROM user WHERE name = ?
    ==> Parameters: xiaofan(String)
    <==    Columns: id, name, age, email, version
    <==        Row: 1, xiaofan, 180, test1@baomidou.com, 3
    <==      Total: 1
    Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@7cca01a8]
    User(id=1, name=xiaofan, age=180, email=test1@baomidou.com, version=3)
  ```
  
- 分页查询

    1. 配置分页拦截器
    
    ```java
      //分页查询配置
          @Bean
          public PaginationInterceptor paginationInterceptor() {
              PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
              // 设置请求的页面大于最大页后操作， true调回到首页，false 继续请求  默认false
              // paginationInterceptor.setOverflow(false);
              // 设置最大单页限制数量，默认 500 条，-1 不受限制
              // paginationInterceptor.setLimit(500);
              // 开启 count 的 join 优化,只针对部分 left join
              paginationInterceptor.setCountSqlParser(new JsqlParserCountOptimize(true));
              return paginationInterceptor;
          }
   ```
  
  2. 测试类
  
  ```java
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
   ```
  
  3. 结果打印
  
  ```text
    JDBC Connection [HikariProxyConnection@875768717 wrapping com.mysql.jdbc.JDBC4Connection@724b939e] will not be managed by Spring
    JsqlParserCountOptimize sql=SELECT  id,name,age,email,version  FROM user
    ==>  Preparing: SELECT COUNT(1) FROM user
    ==> Parameters: 
    <==    Columns: COUNT(1)
    <==        Row: 5
    ==>  Preparing: SELECT id,name,age,email,version FROM user LIMIT ?
    ==> Parameters: 2(Long)
    <==    Columns: id, name, age, email, version
    <==        Row: 1, xiaofan, 180, test1@baomidou.com, 3
    <==        Row: 2, Jack, 20, test2@baomidou.com, 1
    <==      Total: 2
    Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@4730e0f0]
    User(id=1, name=xiaofan, age=180, email=test1@baomidou.com, version=3)
    User(id=2, name=Jack, age=20, email=test2@baomidou.com, version=1)
    2020-09-16 23:52:04.474  INFO 7968 --- [           main] c.ryan.crud.ServiceCrudApplicationTests  : 记录总数：5
   ```
  
  
- 删除操作

    ```java
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
    ```
  
  
- 逻辑删除

    1. 物理删除：直接在数据表上进行删除，永久删除
    
    2. 逻辑删除：主要核心思想还是添加一个字段，用来标记该条是否可以显示，就相当于一个标志位，并非真正意义上的删除
    
    - 数据表中添加字段
    
    - 配置
    
    ```yaml
    
    mybatis-plus:
      global-config:
        db-config:
          logic-delete-field: flag  # 全局逻辑删除的实体字段名(since 3.3.0,配置后可以忽略不配置步骤2)
          logic-delete-value: 1 # 逻辑已删除值(默认为 1)
          logic-not-delete-value: 0 # 逻辑未删除值(默认为 0)
    ```
  
   - 实体类
   ```java
    package com.ryan.crud.entity;
    
    import com.baomidou.mybatisplus.annotation.TableLogic;
    import com.baomidou.mybatisplus.annotation.Version;
    import lombok.AllArgsConstructor;
    import lombok.Data;
    import lombok.NoArgsConstructor;
    
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class User {
        private Long id;
        private String name;
        private Integer age;
        private String email;
    
        @Version
        private Integer version;
    
        @TableLogic
        private Integer deleted;
    
    }

   ```
   
   - 测试代码
   
   ```java
      /**
         * 逻辑删除
         */
        @Test
        void logicDeleteTest() {
            userMapper.deleteBatchIds(Arrays.asList(1, 2, 3, 4));
            userMapper.selectList(null).forEach(System.out::println);
        }
  ```
  
   - 打印结果
   
   ```test
  Creating a new SqlSession
  SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@553bc36c] was not registered for synchronization because synchronization is not active
  2020-09-17 00:38:33.958  INFO 16552 --- [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Starting...
  2020-09-17 00:38:34.322  INFO 16552 --- [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Start completed.
  JDBC Connection [HikariProxyConnection@1250956357 wrapping com.mysql.jdbc.JDBC4Connection@67f77f6e] will not be managed by Spring
  ==>  Preparing: UPDATE user SET deleted=1 WHERE id IN ( ? , ? , ? , ? ) AND deleted=0
  ==> Parameters: 1(Integer), 2(Integer), 3(Integer), 4(Integer)
  <==    Updates: 4
  Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@553bc36c]
  Creating a new SqlSession
  SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@ea52184] was not registered for synchronization because synchronization is not active
  JDBC Connection [HikariProxyConnection@1621202291 wrapping com.mysql.jdbc.JDBC4Connection@67f77f6e] will not be managed by Spring
  ==>  Preparing: SELECT id,name,age,email,version,deleted FROM user WHERE deleted=0
  ==> Parameters: 
  <==    Columns: id, name, age, email, version, deleted
  <==        Row: 5, Billie, 24, test5@baomidou.com, 1, 0
  <==      Total: 1
  Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@ea52184]
  User(id=5, name=Billie, age=24, email=test5@baomidou.com, version=1, deleted=0)

   ```


  
  
    
    
        
    
 

