package com.llf.ssm.util;


import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;

import java.util.ArrayList;
import java.util.List;

public class MybatisPlusGenerator {

    // 需要自动生成的表，多个用逗号隔开
    private static String[] tables = {"test"};
    // 作者，注释里使用
    private static String author = "李良发";
    // 项目路径
    private static String projectDir = "C:/Users/risc64/Desktop/ssm";

    // 包名
    private static String basicPackage = "com.llf.ssm";
    // 包路径
    private static String basicPackageDir = "/com/llf/ssm";

    // 数据库驱动名
    private static String driverName = "com.mysql.jdbc.Driver";
    // 数据库连接
    private static String url = "jdbc:mysql://192.168.1.14:3306/project?useUnicode=true&amp;useSSL=false";
    // 数据库用户名
    private static String username = "test";
    // 数据库密码
    private static String password = "test";

    // 项目Java路径
    private static String outputDir = projectDir + "/src/main/java";
    // 项目资源路径
    private static String basicResourceDir = projectDir + "/src/main/resources";
    // 模块名——暂不使用
    private static String moduleName = "";

    public static void main(String[] args) {
       create(); 
    }

    public static void create() {
        //1. 全局配置
        GlobalConfig config = new GlobalConfig();
        config.setAuthor(author) // 作者
                //.setOutputDir("D:/workspace_mp/mp03/src/main/java") // 生成路径
                .setOutputDir(outputDir) // 生成路径
                .setFileOverride(true)  // 文件覆盖
                //.setIdType(IdType.AUTO) // 主键策略
                .setServiceName("%sService")  // 设置生成的service接口的名字的首字母是否为I
                .setMapperName("%sDao")
                .setEnableCache(false)
                // IEmployeeService
                .setBaseResultMap(true)//生成基本的resultMap
                .setBaseColumnList(true);//生成基本的SQL片段

        //2. 数据源配置
        DataSourceConfig dsConfig  = new DataSourceConfig();
        dsConfig.setDbType(DbType.MYSQL)  // 设置数据库类型
                .setDriverName(driverName)
                .setUrl(url)
                .setUsername(username)
                .setPassword(password);

        //3. 策略配置globalConfiguration中
        StrategyConfig stConfig = new StrategyConfig();
        stConfig.setCapitalMode(true) //全局大写命名
                //.setDbColumnUnderline(true)  // 指定表名 字段名是否使用下划线
//                 字段生成策略，四种类型，从名称就能看出来含义：
//        nochange(默认),
//                underline_to_camel,(下划线转驼峰)
//                remove_prefix,(去除第一个下划线的前部分，后面保持不变)
//        remove_prefix_and_camel(去除第一个下划线的前部分，后面转驼峰)
                .setNaming(NamingStrategy.underline_to_camel) // 数据库表映射到实体的命名策略
                //.setTablePrefix("tbl_")
                .setInclude(tables);  // 生成的表

        //4. 包名策略配置
        PackageConfig pkConfig = new PackageConfig();
        pkConfig.setParent(basicPackage)
                .setModuleName(moduleName)
                .setMapper("dao")//dao
                .setService("service")//servcie
                .setController("controller")//controller
                .setEntity("bo")
                .setXml("mapper");//mapper.xml
         /*自定义文件部分*/
        InjectionConfig inConfig = new InjectionConfig() {
            @Override
            public void initMap() {

            }
        };

        List<FileOutConfig> files = new ArrayList<FileOutConfig>();
        FileOutConfig bo = new FileOutConfig("/templates/llf_bo.java.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return outputDir + basicPackageDir + "/bo/" + tableInfo.getEntityName() + ".java";
            }
        };
        FileOutConfig dao = new FileOutConfig("/templates/llf_dao.java.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return outputDir + basicPackageDir + "/dao/" + tableInfo.getMapperName() + ".java";
            }
        };
        FileOutConfig mapper = new FileOutConfig("/templates/llf_mapper.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return basicResourceDir +  "/mybatis/mapper/" + tableInfo.getXmlName() + ".xml";

            }
        };
        FileOutConfig service = new FileOutConfig("/templates/llf_service.java.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return outputDir + basicPackageDir +  "/service/" + tableInfo.getServiceName() + ".java";
            }
        };
        FileOutConfig serviceImpl = new FileOutConfig("/templates/llf_serviceImpl.java.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return outputDir + basicPackageDir +  "/service/impl/" + tableInfo.getServiceImplName() + ".java";
            }
        };
        FileOutConfig controller = new FileOutConfig("/templates/llf_controller.java.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return outputDir + basicPackageDir + "/controller/" + tableInfo.getControllerName() + ".java";
            }
        };

        files.add(bo);
        files.add(dao);
        files.add(mapper);
        files.add(service);
        files.add(serviceImpl);
        files.add(controller);

        inConfig.setFileOutConfigList(files);

        // 自定义模板配置，可以 copy 源码 mybatis-plus/src/main/resources/templates 下面内容修改，
        // 放置自己项目的 src/main/resources/templates 目录下, 默认名称一下可以不配置，也可以自定义模板名称
         TemplateConfig tc = new TemplateConfig();
         tc.setController(null);
         tc.setEntity(null);
         tc.setMapper(null);
         tc.setXml(null);
         tc.setService(null);
         tc.setServiceImpl(null);


        //VelocityTemplateEngine engine = new VelocityTemplateEngine();
        //engine.init(new ConfigBuilder(pkConfig,dsConfig,stConfig,tc,config));
        //engine.templateFilePath("F:/My Data/3 Code/5 project/src/main/resources/templates");
        //5. 整合配置
        AutoGenerator ag = new AutoGenerator();
        ag.setGlobalConfig(config)
                .setDataSource(dsConfig)
                .setStrategy(stConfig)
                .setTemplate(tc)
                //.setTemplateEngine(engine)
                .setTemplateEngine(new VelocityTemplateEngine())
                .setPackageInfo(pkConfig)
                .setCfg(inConfig);
        //ag.setTemplateEngine(new VelocityTemplateEngine());
        //6. 执行
        try{
            ag.execute();
            //engine.open();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
   }


}
