package com.ryan.autogenerator;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication

public class AutoGeneratorApplication {

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
            // 代码生成器
            AutoGenerator mpg = new AutoGenerator();

            // 全局配置
            GlobalConfig globalConfig = new GlobalConfig();
            String projectPath = System.getProperty("user.dir");
            globalConfig.setOutputDir(projectPath + "/src/main/java");
            globalConfig.setAuthor("fuyi");
            globalConfig.setFileOverride(false); //文件是否覆盖
            globalConfig.setServiceName("%sService"); //去Service前缀I
            globalConfig.setOpen(false);              //是否打开资源管理器
            globalConfig.setIdType(IdType.ID_WORKER); //设置主键id策略
            globalConfig.setDateType(DateType.ONLY_DATE); //设置日期格式使用 java.util.date 代替
            globalConfig.setSwagger2(true);            //配置Swagger文档
            mpg.setGlobalConfig(globalConfig);

            //数据源配置
            DataSourceConfig dataSourceConfig = new DataSourceConfig();
            dataSourceConfig.setUrl("jdbc:mysql://47.112.240.174:3306/mybatis-plus?useUnicode=true&useSSL=false&characterEncoding=utf8");
            dataSourceConfig.setDbType(DbType.MYSQL);      //设置支持的数据类型
            dataSourceConfig.setDriverName("com.mysql.jdbc.Driver");
            dataSourceConfig.setUsername("root");
            dataSourceConfig.setPassword("MyNewPass4!");
            mpg.setDataSource(dataSourceConfig);

            // 包配置
            PackageConfig pc = new PackageConfig();
            pc.setModuleName(scanner("模块名"));
            pc.setParent("com.ryan");
            pc.setEntity("entity");
            pc.setMapper("mapper");
            pc.setService("service");
            pc.setController("controller");
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
            cfg.setFileOutConfigList(focList);
            mpg.setCfg(cfg);

            // 配置模板
            TemplateConfig templateConfig = new TemplateConfig();
            templateConfig.setXml(null);
            mpg.setTemplate(templateConfig);

            // 策略配置
            StrategyConfig strategy = new StrategyConfig();
            strategy.setInclude(scanner("表名，多个英文逗号分割").split(","));   //设置映射的表名
            strategy.setNaming(NamingStrategy.underline_to_camel);          //设置驼峰命名
            strategy.setColumnNaming(NamingStrategy.underline_to_camel);    //列名直接转驼峰
            strategy.setEntityLombokModel(true);                            //自动生成Lombok
            strategy.setRestControllerStyle(true);                          //开启Controller驼峰命名规则
            strategy.setLogicDeleteFieldName(scanner("逻辑删除的列名"));             //设置逻辑删除的列名字段

            //自动填充策略配置
            TableFill createTime = new TableFill("create_time", FieldFill.INSERT);
            TableFill updateTime = new TableFill("update_time", FieldFill.INSERT_UPDATE);
            strategy.setTableFillList(Arrays.asList(createTime, updateTime));

            //乐观锁配置
            strategy.setVersionFieldName(scanner("乐观锁字段"));

            mpg.setStrategy(strategy);
            mpg.setTemplateEngine(new FreemarkerTemplateEngine());
            mpg.execute();
        }


}
