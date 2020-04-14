package com.sample.app.generator;


import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.BeetlTemplateEngine;

import java.util.HashMap;
import java.util.Map;

public class Generator {
	
	//核心类，所有操作配置都围绕该类展开。
    private static final AutoGenerator mpg = new AutoGenerator();
    //获取项目目录
    private static final String projectPath = "D:\\my-git\\spring-boot-study\\spring-boot-mybatis-plus";
    //项目基础包
    private static final String Package = "com.sample.app";
    //子模块包名，最终生成的是类似  com.ovi.train.mobile.server.dev 这样的
    // private static final String ModuleName = "dev";

    public static void main(String[] args) {
        //数据库连接配置
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setUrl("jdbc:mysql://localhost:3306/mp?useUnicode=true&useSSL=false&characterEncoding=utf8&serverTimezone=GMT%2B8");
        dataSourceConfig.setDriverName("com.mysql.cj.jdbc.Driver");
        dataSourceConfig.setUsername("root");
        dataSourceConfig.setPassword("root");
        mpg.setDataSource(dataSourceConfig);
        //公用的一些配置，一看就懂的就不加注释了。
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setOutputDir(projectPath + "/src/main/java");
        // globalConfig.setAuthor("sun");
        //日期类型的字段使用哪个类型，默认是 java8的 日期类型，此处改为 java.util.date
        globalConfig.setDateType(DateType.ONLY_DATE);
        //是否覆盖 已存在文件，默认 false 不覆盖
        globalConfig.setFileOverride(false);
        //mapper.xml 是否生成 ResultMap，默认 false 不生成
        globalConfig.setBaseResultMap(true);
        //mapper.xml 是否生成 ColumnList，默认 false 不生成
        globalConfig.setBaseColumnList(true);
        //生成的实体类名字，增加前后缀的 ，下面的mapper xml 同理,另外还有controller和service的名称配置
        // globalConfig.setEntityName("%sEntity");
        // 自定义文件命名，注意 %s 会自动填充表实体属性！
        globalConfig.setControllerName("%sController");
        globalConfig.setServiceName("%sService");
        globalConfig.setServiceImplName("%sServiceImpl");
        globalConfig.setMapperName("%sMapper");
        globalConfig.setXmlName("%sMapper");
        //是否生成完成后打开资源管理器
        globalConfig.setOpen(false);
        mpg.setGlobalConfig(globalConfig);

        PackageConfig pc = new PackageConfig();
        pc.setParent(Package);
        // 模块名
        // pc.setModuleName(ModuleName);
        mpg.setPackageInfo(pc);

        StrategyConfig strategy = new StrategyConfig();
        //支持正则表达式，字符串数组。 和 Exclude 二选一
        // TODO 此处使用正则生成时，提示有些bug。
        //  被正则过滤的表会提示 “^dev_.*$” 表不存在，其实需要生成代码的表已经正常生成完毕了。
        // strategy.setInclude("^dev_.*$");
        strategy.setInclude("user");
        //  不需要生成的表
        //  strategy.setExclude("sequence");
        //此处配置为 下划线转驼峰命名
        strategy.setNaming(NamingStrategy.underline_to_camel);
        //生成的字段 是否添加注解，默认false
        strategy.setEntityTableFieldAnnotationEnable(true);
        //表前缀，配置后 生成的的代码都会把前缀去掉
        // strategy.setTablePrefix("sys_");
        //实体类的基础父类。没有可以不配置。
        strategy.setSuperEntityClass("com.sample.app.entity.BaseEntity");
        //这里本来以为配置上 生成的实体类就没有父类的属性了，但其实不是。
        //如何去掉父类属性，下面有说明。
        strategy.setSuperEntityColumns("createBy","createTime","updateBy","updateTime","delFlag");
        //controller,基础父类
        strategy.setSuperControllerClass("com.sample.app.controller.BaseController");
        //是否启用 Lombok
        strategy.setEntityLombokModel(true);
        //是否启用 builder 模式 例：new DevDevice().setDealerId("").setDeviceCode("");
        strategy.setEntityBuilderModel(true);
        
        mpg.setStrategy(strategy);

        InjectionConfig in = new InjectionConfig() {
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<String, Object>();
                //自定义配置，在模版中cfg.superColums 获取
                // TODO 这里解决子类会生成父类属性的问题，在模版里会用到该配置
                map.put("superColums", this.getConfig().getStrategyConfig().getSuperEntityColumns());
                this.setMap(map);
            }
        };
        in.setFileOutConfigList(CollectionUtil.newArrayList(new FileOutConfig("/templates/mapper.xml.btl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义mapper xml输出目录
                return projectPath + "/src/main/resources/mapper/" 
                		// + ModuleName + "/" 
                		+ tableInfo.getMapperName()  + StringPool.DOT_XML;
            }
        }));
        mpg.setCfg(in);
        TemplateConfig templateConfig = new TemplateConfig();
        //自定义模版
        // templateConfig.setController("/templates/btl/controller.java");
        // templateConfig.setEntity("/templates/btl/entity.java");
        //关闭默认的mapper xml生成
        templateConfig.setXml(null);
        mpg.setTemplate(templateConfig);
        //使用beetl模版引擎
        mpg.setTemplateEngine(new BeetlTemplateEngine());
        mpg.execute();
    }

}
