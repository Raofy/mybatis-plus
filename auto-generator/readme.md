# 代码生成器

作用：可以快速生成Mapper，service，entity和xml文件代码，提高开发效率

- 工程环境搭建

    - 添加依赖
    
    ```xml
    <?xml version="1.0" encoding="UTF-8"?>
    <project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
             xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
        <modelVersion>4.0.0</modelVersion>
        <parent>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-parent</artifactId>
            <version>2.3.3.RELEASE</version>
            <relativePath/> <!-- lookup parent from repository -->
        </parent>
        <groupId>com.Ryan</groupId>
        <artifactId>auto-generator</artifactId>
        <version>0.0.1-SNAPSHOT</version>
        <name>auto-generator</name>
        <description>Demo project for Spring Boot</description>
    
        <properties>
            <java.version>1.8</java.version>
        </properties>
    
        <dependencies>
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-starter</artifactId>
            </dependency>
    
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-starter-test</artifactId>
                <scope>test</scope>
                <exclusions>
                    <exclusion>
                        <groupId>org.junit.vintage</groupId>
                        <artifactId>junit-vintage-engine</artifactId>
                    </exclusion>
                </exclusions>
            </dependency>
    
            <!--        配置Mysql依赖-->
            <dependency>
                <groupId>mysql</groupId>
                <artifactId>mysql-connector-java</artifactId>
                <version>5.1.49</version>
                <scope>runtime</scope>
            </dependency>
    
            <!--        配置lombok依赖-->
            <dependency>
                <groupId>org.projectlombok</groupId>
                <artifactId>lombok</artifactId>
                <optional>true</optional>
            </dependency>
    
            <!--        配置Mybatis-plus依赖-->
            <dependency>
                <groupId>com.baomidou</groupId>
                <artifactId>mybatis-plus-boot-starter</artifactId>
                <version>3.4.0</version>
            </dependency>
    
            <!--freemarker代码生成模板引擎-->
            <dependency>
                <groupId>org.freemarker</groupId>
                <artifactId>freemarker</artifactId>
                <version>2.3.30</version>
            </dependency>
    
        </dependencies>
    
        <build>
            <plugins>
                <plugin>
                    <groupId>org.springframework.boot</groupId>
                    <artifactId>spring-boot-maven-plugin</artifactId>
                </plugin>
            </plugins>
        </build>
    
    </project>
    ```
  - 编写配置Java类
  
    **[更多参数配置详情](https://baomidou.com/config/generator-config.html#drivername)**
  
  ```java
  /**
       * <p>
       * 读取控制台内容
       * </p>
       */
      public static String scanner(String tip) {
          Scanner scanner = new Scanner(System.in);
          StringBuilder help = new StringBuilder();
          help.append("请输入" + tip + "：");
          System.out.println(help.toString());
          if (scanner.hasNext()) {
              String ipt = scanner.next();
              if (StringUtils.isNotBlank(ipt)) {
                  return ipt;
              }
          }
          throw new MybatisPlusException("请输入正确的" + tip + "！");
      }
      public static void main(String[] args) {
  //        SpringApplication.run(AutoGeneratorApplication.class, args);
          // 代码生成器
          AutoGenerator mpg = new AutoGenerator();
  
          // 全局配置
          GlobalConfig globalConfig = new GlobalConfig();
          String projectPath = System.getProperty("user.dir");
          globalConfig.setOutputDir(projectPath + "/src/main/java");
          globalConfig.setAuthor("fuyi");
          globalConfig.setOpen(false);
          mpg.setGlobalConfig(globalConfig);
  
          //数据源配置
          DataSourceConfig dataSourceConfig = new DataSourceConfig();
          dataSourceConfig.setUrl("jdbc:mysql://localhost:3306/mybatisplus?useUnicode=true&useSSL=false&characterEncoding=utf8");
          dataSourceConfig.setDriverName("com.mysql.jdbc.Driver");
          dataSourceConfig.setUsername("root");
          dataSourceConfig.setPassword("123456");
          mpg.setDataSource(dataSourceConfig);
  
          // 包配置
          PackageConfig pc = new PackageConfig();
          pc.setModuleName(scanner("模块名"));
          pc.setParent("com.ryan.mybatisplus");
          mpg.setPackageInfo(pc);
  
          // 自定义配置
          InjectionConfig cfg = new InjectionConfig() {
              @Override
              public void initMap() {
                  // to do nothing
              }
          };
  
          // 如果模板引擎是 freemarker
          String templatePath = "/templates/mapper.xml.ftl";
          // 如果模板引擎是 velocity
          // String templatePath = "/templates/mapper.xml.vm";
  
          // 自定义输出配置
          List<FileOutConfig> focList = new ArrayList<>();
          // 自定义配置会被优先输出
          focList.add(new FileOutConfig(templatePath) {
              @Override
              public String outputFile(TableInfo tableInfo) {
                  // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                  return projectPath + "/src/main/resources/mapper/" + pc.getModuleName()
                          + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
              }
          });
          /*
          cfg.setFileCreate(new IFileCreate() {
              @Override
              public boolean isCreate(ConfigBuilder configBuilder, FileType fileType, String filePath) {
                  // 判断自定义文件夹是否需要创建
                  checkDir("调用默认方法创建的目录，自定义目录用");
                  if (fileType == FileType.MAPPER) {
                      // 已经生成 mapper 文件判断存在，不想重新生成返回 false
                      return !new File(filePath).exists();
                  }
                  // 允许生成模板文件
                  return true;
              }
          });
          */
          cfg.setFileOutConfigList(focList);
          mpg.setCfg(cfg);
  
          // 配置模板
          TemplateConfig templateConfig = new TemplateConfig();
  
          // 配置自定义输出模板
          //指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
          // templateConfig.setEntity("templates/entity2.java");
          // templateConfig.setService();
          // templateConfig.setController();
  
          templateConfig.setXml(null);
          mpg.setTemplate(templateConfig);
  
          // 策略配置
          StrategyConfig strategy = new StrategyConfig();
          strategy.setNaming(NamingStrategy.underline_to_camel);
          strategy.setColumnNaming(NamingStrategy.underline_to_camel);
          strategy.setSuperEntityClass(BaseMapper.class);
          strategy.setEntityLombokModel(true);
          strategy.setRestControllerStyle(true);
          // 公共父类
  //        strategy.setSuperControllerClass("你自己的父类控制器,没有就不用设置!");
          // 写于父类中的公共字段
          strategy.setSuperEntityColumns("id");
          strategy.setInclude(scanner("表名，多个英文逗号分割").split(","));
          strategy.setControllerMappingHyphenStyle(true);
          strategy.setTablePrefix(pc.getModuleName() + "_");
          mpg.setStrategy(strategy);
          mpg.setTemplateEngine(new FreemarkerTemplateEngine());
          mpg.execute();
      }
  ```
  
  - 执行结果
  
  ```text
  请输入模块名：
  fuyi
  11:17:26.747 [main] DEBUG org.apache.ibatis.logging.LogFactory - Logging initialized using 'class org.apache.ibatis.logging.slf4j.Slf4jImpl' adapter.
  请输入表名，多个英文逗号分割：
  user
  11:17:38.239 [main] DEBUG com.baomidou.mybatisplus.generator.AutoGenerator - ==========================准备生成文件...==========================
  11:17:38.481 [main] DEBUG com.baomidou.mybatisplus.generator.engine.AbstractTemplateEngine - 创建目录： [C:\Users\13121\Desktop\mybatis-plus/src/main/java\com\ryan\mybatisplus\fuyi\entity]
  11:17:38.481 [main] DEBUG com.baomidou.mybatisplus.generator.engine.AbstractTemplateEngine - 创建目录： [C:\Users\13121\Desktop\mybatis-plus/src/main/java\com\ryan\mybatisplus\fuyi\controller]
  11:17:38.482 [main] DEBUG com.baomidou.mybatisplus.generator.engine.AbstractTemplateEngine - 创建目录： [C:\Users\13121\Desktop\mybatis-plus/src/main/java\com\ryan\mybatisplus\fuyi\mapper]
  11:17:38.483 [main] DEBUG com.baomidou.mybatisplus.generator.engine.AbstractTemplateEngine - 创建目录： [C:\Users\13121\Desktop\mybatis-plus/src/main/java\com\ryan\mybatisplus\fuyi\service\impl]
  11:17:38.520 [main] DEBUG com.baomidou.mybatisplus.generator.engine.AbstractTemplateEngine - 模板:/templates/mapper.xml.ftl;  文件:C:\Users\13121\Desktop\mybatis-plus/src/main/resources/mapper/fuyi/UserMapper.xml
  11:17:38.595 [main] DEBUG com.baomidou.mybatisplus.generator.engine.AbstractTemplateEngine - 模板:/templates/entity.java.ftl;  文件:C:\Users\13121\Desktop\mybatis-plus/src/main/java\com\ryan\mybatisplus\fuyi\entity\User.java
  11:17:38.597 [main] DEBUG com.baomidou.mybatisplus.generator.engine.AbstractTemplateEngine - 模板:/templates/mapper.java.ftl;  文件:C:\Users\13121\Desktop\mybatis-plus/src/main/java\com\ryan\mybatisplus\fuyi\mapper\UserMapper.java
  11:17:38.598 [main] DEBUG com.baomidou.mybatisplus.generator.engine.AbstractTemplateEngine - 模板:/templates/service.java.ftl;  文件:C:\Users\13121\Desktop\mybatis-plus/src/main/java\com\ryan\mybatisplus\fuyi\service\IUserService.java
  11:17:38.600 [main] DEBUG com.baomidou.mybatisplus.generator.engine.AbstractTemplateEngine - 模板:/templates/serviceImpl.java.ftl;  文件:C:\Users\13121\Desktop\mybatis-plus/src/main/java\com\ryan\mybatisplus\fuyi\service\impl\UserServiceImpl.java
  11:17:38.602 [main] DEBUG com.baomidou.mybatisplus.generator.engine.AbstractTemplateEngine - 模板:/templates/controller.java.ftl;  文件:C:\Users\13121\Desktop\mybatis-plus/src/main/java\com\ryan\mybatisplus\fuyi\controller\UserController.java
  11:17:38.602 [main] DEBUG com.baomidou.mybatisplus.generator.AutoGenerator - ==========================文件生成完成！！！==========================
  ```
  
  - 结果
  
  ![生成结构图](../screenshots/代码自动生成器结构.png)
  

    