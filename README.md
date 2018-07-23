CRM（<u>C</u>ustomer <u>R</u>elationship <u>M</u>anagement System）Practise, based on the technology of MyBatis and Spring MVC

## 1 Development Environment

| IDE      | IntelliJ IDEA 2018.1 on macOS |
| -------- | ----------------------------- |
| JDK      | 1.8                           |
| Database | MySQL                         |

## 2 Create a Database with 3 tables

### 2.1 Script Files to create database

#### 1 `Base_dict, customer and sys_user`

```sql
CREATE TABLE base_dict
(
    dict_id VARCHAR(32) NOT NULL
    COMMENT '数据字典id(主键)'
        PRIMARY KEY,
    dict_type_code VARCHAR(10) NOT NULL
    COMMENT '数据字典类别代码',
    dict_type_name VARCHAR(64) NOT NULL
    COMMENT '数据字典类别名称',
    dict_item_name VARCHAR(64) NOT NULL
    COMMENT '数据字典项目名称',
    dict_item_code VARCHAR(10) NULL
    COMMENT '数据字典项目代码(可为空)',
    dict_sort INT(10) NULL
    COMMENT '排序字段',
    dict_enable CHAR NOT NULL
    COMMENT '1:使用 0:停用',
    dict_memo VARCHAR(64) NULL
    COMMENT '备注'
)
    ENGINE = InnoDB;

CREATE TABLE customer
(
    cust_id BIGINT(32) AUTO_INCREMENT
    COMMENT '客户编号(主键)'
        PRIMARY KEY,
    cust_name VARCHAR(32) NOT NULL
    COMMENT '客户名称(公司名称)',
    cust_user_id BIGINT(32) NULL
    COMMENT '负责人id',
    cust_create_id BIGINT(32) NULL
    COMMENT '创建人id',
    cust_source VARCHAR(32) NULL
    COMMENT '客户信息来源',
    cust_industry VARCHAR(32) NULL
    COMMENT '客户所属行业',
    cust_level VARCHAR(32) NULL
    COMMENT '客户级别',
    cust_linkman VARCHAR(64) NULL
    COMMENT '联系人',
    cust_phone VARCHAR(64) NULL
    COMMENT '固定电话',
    cust_mobile VARCHAR(50) NULL
    COMMENT '移动电话',
    cust_zipcode VARCHAR(10) NULL,
    cust_address VARCHAR(100) NULL,
    cust_createtime DATETIME NULL
    COMMENT '创建时间'
)
    ENGINE = InnoDB;

CREATE INDEX FK_cst_customer_create_id
    ON customer (cust_create_id);

CREATE INDEX FK_cst_customer_industry
    ON customer (cust_industry);

CREATE INDEX FK_cst_customer_level
    ON customer (cust_level);

CREATE INDEX FK_cst_customer_source
    ON customer (cust_source);

CREATE INDEX FK_cst_customer_user_id
    ON customer (cust_user_id);

CREATE TABLE sys_user
(
    user_id BIGINT(32) AUTO_INCREMENT
    COMMENT '用户id'
        PRIMARY KEY,
    user_code VARCHAR(32) NOT NULL
    COMMENT '用户账号',
    user_name VARCHAR(64) NOT NULL
    COMMENT '用户名称',
    user_password VARCHAR(32) NOT NULL
    COMMENT '用户密码',
    user_state CHAR NOT NULL
    COMMENT '1:正常,0:暂停'
)
    ENGINE = InnoDB;
```

#### 2 Insert data

##### `CRM_base_dict.sql`

```sql
INSERT INTO CRM.base_dict (dict_id, dict_type_code, dict_type_name, dict_item_name, dict_item_code, dict_sort, dict_enable, dict_memo)
VALUES ('1', '001', '客户行业', '教育培训 ', NULL, 1, '1', NULL);
INSERT INTO CRM.base_dict (dict_id, dict_type_code, dict_type_name, dict_item_name, dict_item_code, dict_sort, dict_enable, dict_memo)
VALUES ('10', '003', '公司性质', '民企', NULL, 3, '1', NULL);
...
```

##### `CRM_sys_user.sql`


```sql
INSERT INTO CRM.sys_user (user_id, user_code, user_name, user_password, user_state) VALUES (13273153, 'aburcomben', '雨萌', 'E3pEaEG', '0');
INSERT INTO CRM.sys_user (user_id, user_code, user_name, user_password, user_state) VALUES (17397069, 'mdrinkallx', '亦涵', 'tfZHHSBW', '1');
INSERT INTO CRM.sys_user (user_id, user_code, user_name, user_password, user_state) VALUES (19866137, 'amorcombe12', '昕磊', '0ZGxblD', '0');
...
```

##### `CRM_customer.sql`

```sql
INSERT INTO CRM.customer (cust_id, cust_name, cust_user_id, cust_create_id, cust_source, cust_industry, cust_level, cust_linkman, cust_phone, cust_mobile, cust_zipcode, cust_address, cust_createtime) VALUES (10067716, '淑颖', null, null, '7', '4', '23', 'Gerardo Holland', '(133) 4352491', '+63 (300) 596-8299', null, '2 Moulton Avenue', '2018-02-21 00:00:00');
INSERT INTO CRM.customer (cust_id, cust_name, cust_user_id, cust_create_id, cust_source, cust_industry, cust_level, cust_linkman, cust_phone, cust_mobile, cust_zipcode, cust_address, cust_createtime) VALUES (10087095, '松源', null, null, '7', '4', '22', 'Ellen Hamilton', '(670) 8600765', '+86 (551) 708-3767', null, '20 Norway Maple Center', '2018-06-13 00:00:00');
INSERT INTO CRM.customer (cust_id, cust_name, cust_user_id, cust_create_id, cust_source, cust_industry, cust_level, cust_linkman, cust_phone, cust_mobile, cust_zipcode, cust_address, cust_createtime) VALUES (10135551, '远帆', null, null, '6', '2', '23', 'Sherman Alexander', '(967) 2117876', '+86 (643) 472-9137', null, '44329 Briar Crest Trail', '2018-07-17 00:00:00');
...
```

## 3 Build a development environment

For front end, introduce BootStrap Framework to create a better browsing experience, together with Spring MVC, Spring and MyBatis.

### 3.1 Dependency packages of jar

1. Spring MVC
2. MyBatis
3. Integration package of Spring and MyBatis
4. Database driver
5. Database connection pool
6. JSON's dependencies
7. Java EE 6

```powershell
.
├── aopalliance-1.0.jar
├── asm-3.3.1.jar
├── aspectjweaver-1.8.4.jar
├── cglib-2.2.2.jar
├── commons-codec-1.6.jar
├── commons-collections-1.0.jar
├── commons-fileupload-1.2.2.jar
├── commons-io-1.3.2.jar
├── commons-lang3-3.1.jar
├── commons-lang3-3.4.jar
├── commons-logging-1.2.jar
├── commons-pool2-2.0.jar
├── dom4j-1.6.1.jar
├── druid-1.0.9.jar
├── hamcrest-core-1.3.jar
├── jackson-annotations-2.4.0.jar
├── jackson-core-2.4.2.jar
├── jackson-databind-2.4.2.jar
├── javax.annotation.jar
├── javax.ejb.jar
├── javax.jms.jar
├── javax.persistence.jar
├── javax.resource.jar
├── javax.servlet.jar
├── javax.servlet.jsp.jar
├── javax.transaction.jar
├── json-20131018.jar
├── jstl-1.2.jar
├── junit-4.12.jar
├── log4j-1.2.17.jar
├── mybatis-3.2.7.jar
├── mybatis-spring-1.2.2.jar
├── mysql-connector-java-5.1.8.jar
├── slf4j-api-1.6.6.jar
├── slf4j-log4j12-1.6.6.jar
├── spring-aop-4.2.4.RELEASE.jar
├── spring-aspects-4.2.4.RELEASE.jar
├── spring-beans-4.2.4.RELEASE.jar
├── spring-context-4.2.4.RELEASE.jar
├── spring-context-support-4.2.4.RELEASE.jar
├── spring-core-4.2.4.RELEASE.jar
├── spring-expression-4.2.4.RELEASE.jar
├── spring-jdbc-4.2.4.RELEASE.jar
├── spring-jms-4.2.4.RELEASE.jar
├── spring-messaging-4.2.4.RELEASE.jar
├── spring-tx-4.2.4.RELEASE.jar
├── spring-web-4.2.4.RELEASE.jar
├── spring-webmvc-4.2.4.RELEASE.jar
└── standard-1.1.2.jar
```

### 3.2 Strategies of integration

#### 3.2.1 dao Layer

1. `SqlMapConfig.xml`

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
</configuration>
```

2. `applicationContext-dao.xml`

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-4.0.xsd
	http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context-4.0.xsd">

    <!-- 配置 读取properties文件 jdbc.properties -->
    <context:property-placeholder location="classpath:jdbc.properties"/>

    <!-- 配置 数据源 -->
    <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource">
        <property name="driverClassName" value="${jdbc.driver}"/>
        <property name="url" value="${jdbc.url}"/>
        <property name="username" value="${jdbc.username}"/>
        <property name="password" value="${jdbc.password}"/>
    </bean>

    <!-- 配置SqlSessionFactory -->
    <bean class="org.mybatis.spring.SqlSessionFactoryBean">
        <!-- 设置MyBatis核心配置文件 -->
        <property name="configLocation" value="classpath:SqlMapConfig.xml"/>
        <!-- 设置数据源 -->
        <property name="dataSource" ref="dataSource"/>
        <!-- 别名包扫描 -->
        <property name="typeAliasesPackage" value="com.lightwing.crm.pojo"/>
    </bean>

    <!-- 配置Mapper扫描 -->
    <bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
        <!-- 设置Mapper扫描包 -->
        <property name="basePackage" value="com.lightwing.crm.mapper"/>
    </bean>
</beans>
```

a. Connect to JDBC

b. SqlSessionFactory Object

c. Configure the scanner of mapper

#### 3.2.2 Service Layer

1. `applicationContext-trans.xml`, for @service annotation:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-4.0.xsd
	http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context-4.0.xsd">
    <!-- 配置@Service类的包扫描 -->
    <context:component-scan base-package="com.lightwing.crm.service"/>
</beans>
```

2. `applicationContext-trans.xml`, configure transactions

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xmlns:tx="http://www.springframework.org/schema/tx"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-4.0.xsd
	http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop-4.0.xsd http://www.springframework.org/schema/tx http://www.springframework.org/schema/tx/spring-tx-4.0.xsd">

    <!-- 事务管理器 -->
    <bean id="transactionManager"
          class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
        <!-- 数据源 -->
        <property name="dataSource" ref="dataSource"/>
    </bean>

    <!-- 通知 -->
    <tx:advice id="txAdvice">
        <tx:attributes>
            <!-- 传播行为 -->
            <tx:method name="save*"/>
            <tx:method name="insert*"/>
            <tx:method name="add*"/>
            <tx:method name="create*"/>
            <tx:method name="delete*"/>
            <tx:method name="update*"/>
            <tx:method name="find*" propagation="SUPPORTS" read-only="true"/>
            <tx:method name="select*" propagation="SUPPORTS" read-only="true"/>
            <tx:method name="get*" propagation="SUPPORTS" read-only="true"/>
            <tx:method name="query*" propagation="SUPPORTS" read-only="true"/>
        </tx:attributes>
    </tx:advice>

    <!-- 切面 -->
    <aop:config>
        <aop:advisor advice-ref="txAdvice"
                     pointcut="execution(* com.lightwing.crm.service.*.*(..))"/>
    </aop:config>
</beans>
```

#### 3.2.3 Controller Layer

1. Adding `springmvc.xml` to the same directory for scanning packages, configurations of drivers and view resolvers

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:mvc="http://www.springframework.org/schema/mvc"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-4.0.xsd
        http://www.springframework.org/schema/mvc http://www.springframework.org/schema/mvc/spring-mvc-4.0.xsd
        http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context-4.0.xsd">
    <!-- 配置Controller扫描 -->
    <context:component-scan base-package="com.lightwing.crm.controller"/>
    <!-- 属性文件的加载 -->
    <context:property-placeholder location="classpath:crm.properties"/>
    <!-- 配置注解驱动 -->
    <mvc:annotation-driven/>
    <!-- 配置视图解析器 -->
    <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
        <property name="prefix" value="WEB-INF/jsp"/>
        <property name="suffix" value=".jsp"/>
    </bean>
</beans>
```

2. web.xml

   Configure Srping

   Configure Front end Controller

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
         version="4.0">
    
   	<!-- 配置spring -->
    <context-param>
        <param-name>contextConfigLocation</param-name>
        <param-value>classpath:spring/applicationContext-*.xml</param-value>
    </context-param>

    <!-- 配置监听器加载spring -->
    <listener>
        <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
    </listener>
    
    <!-- 配置过滤器，解决 post 的乱码问题 -->
    <filter>
        <filter-name>encoding</filter-name>
        <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
    </filter>
    <filter-mapping>
        <filter-name>encoding</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>
</web-app>
```

### 3.3 Creating the dynamic Web Project

Create through IDEA, the directory structure should look like this:

```powershell
.
├── .idea
├── BOOT_CRM.iml
├── config
│   ├── SqlMapConfig.xml
│   └── spring
│       ├── applicationContext-dao.xml
│       ├── applicationContext-service.xml
│       ├── applicationContext-trans.xml
│       └── springmvc.xml
├── out
├── src
└── web
    ├── .DS_Store
    ├── WEB-INF
    │   ├── lib
    │   └── web.xml
    └── index.jsp
```

### 3.4 jar packages

### 3.5 Configure JDBC and other properties under `/config`

#### 3.5.1 `SqlMapConfig.xml`

Like previous.

#### 3.5.2 `applicationContext-dao.xml` 

Like previous.

#### 3.5.3 `jdbc.properties`

The database name, username and password shoul be ajusted to your configurations.

```properties
jdbc.driver=com.mysql.jdbc.Driver
jdbc.url=jdbc:mysql://localhost:3306/CRM?characterEncoding=utf-8
jdbc.username=root
jdbc.password=canton0520
```

#### 3.5.4 log4j.properties

For loggers.

```properties
# Global logging configuration
log4j.rootLogger=debug, stdout
# Console output...
log4j.appender.stdout=org.apache.log4j.ConsoleAppender
log4j.appender.stdout.layout=org.apache.log4j.PatternLayout
log4j.appender.stdout.layout.ConversionPattern=%5p [%t] - %m%n
```

#### 3.5.5 `applicationContext-service.xml`

Like previous.

#### 3.5.6 `applicationContext-trans.xml`

Like previous.

#### 3.5.7 `springmvc.xml`

Like previous

#### 3.5.8 `web.xml`

Full version shoul look like this,

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xmlns="http://java.sun.com/xml/ns/javaee"
         xsi:schemaLocation="http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-app_2_5.xsd"
         id="WebApp_ID" version="2.5">
    <display-name>boot-crm</display-name>
    <welcome-file-list>
        <welcome-file>index.html</welcome-file>
    </welcome-file-list>

    <!-- 配置spring -->
    <context-param>
        <param-name>contextConfigLocation</param-name>
        <param-value>classpath:spring/applicationContext-*.xml</param-value>
    </context-param>

    <!-- 配置监听器加载spring -->
    <listener>
        <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
    </listener>

    <!-- 配置过滤器，解决 post 的乱码问题 -->
    <filter>
        <filter-name>encoding</filter-name>
        <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
    </filter>
    <filter-mapping>
        <filter-name>encoding</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>

    <!-- 配置SpringMVC -->
    <servlet>
        <servlet-name>boot-crm</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <init-param>
            <param-name>contextConfigLocation</param-name>
            <param-value>classpath:spring/springmvc.xml</param-value>
        </init-param>

        <!-- 配置 SpringMVC 什么时候启动，参数必须为整数 -->
        <!-- 如果为0或者大于0，则 SpringMVC 随着容器启动而启动 -->
        <!-- 如果小于0，则在第一次请求进来的时候启动 -->
        <load-on-startup>1</load-on-startup>
    </servlet>
    <servlet-mapping>
        <servlet-name>boot-crm</servlet-name>
        <!-- 所有的请求都进入 SpringMVC -->
        <url-pattern>*.action</url-pattern>
    </servlet-mapping>
</web-app>
```

### 3.6 Adding Static Resources

For static web pages, files composed by front-end developers should be added to the `web/` directory, and it shall looked like below:

```powershell
.
├── config
├── out
├── src
└── web
    ├── WEB-INF
    │   └── lib
    ├── css
    ├── fonts
    ├── images
    └── js
```

## 4 Implement of Pages

### 4.1 Controller of Customer:

To display users' list, adding a new package `com.lightwing.crm.controller`, in this directory, adding the first Java Class named `CustomerController.java`, and it should be coded like this:

```java
package com.lightwing.crm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 客户信息请求请求处理
 *
 * @author Lightwing Ng
 */
@Controller
@RequestMapping("customer")
public class CustomerController {
    @RequestMapping("list")
    public String queryCustomerList() {
        return "customer";
    }
}
```

### 4.2 Resolve Page's Displaying Issues

Now, running Tomcat, then the deployment should be automatically executed. But when after that, the website page may look like this,

![]()

If so, the CSS, JavaScript files may not be loadded through checking out if Chrome, you may find all the request were directed into Spring MVC.

So let us fixed that as fellows.

1. Configure in `springmvc.xml`, add this:

```xml
<!--resolve the issue of fixing up Spring MVC-->
<mvc:default-servlet-handler/>
```

2. Modified `web.xml`, guide all the requests end with action to Spring MVC,

```xml
<servlet-mapping>
	<servlet-name>boot-crm</servlet-name>
  	<!-- 所有的请求都进入 SpringMVC -->
	<url-pattern>*.action</url-pattern>
</servlet-mapping>
```

After that, issues would be fixed up, and repeat starting Tomcat and go to `http://localhost:8080/boot-crm/customer/list.action`, and the page should be recover like this.  

![]()

## 5 Implement the enquiry and initialization

### 5.1 The final effect shoul look like this

![]()

When enquiry for the customers, you can select all the targets buy sorting customer souces, customer level and some initialiazation conditions when pull down the list.

Edit this in `WEB-INF/jsp/customer.jsp`

```jsp
  <form class="form-inline" action="${pageContext.request.contextPath }/customer/list.action" method="get"> 
   <div class="form-group"> 
    <label for="customerName">客户名称</label> 
    <input type="text" class="form-control" id="customerName" value="${vo.custName }" name="custName" /> 
   </div> 
   <div class="form-group"> 
    <label for="customerFrom">客户来源</label> 
    <select class="form-control" id="customerFrom" placeholder="客户来源" name="custSource"> <option value="">--请选择--</option>  <option value="${ item.dict_id}" <c:if="" test="${ item.dict_id == vo.custSource}"> selected&gt;${ item.dict_item_name }</option>  </select> 
   </div> 
   <div class="form-group"> 
    <label for="custIndustry">所属行业</label> 
    <select class="form-control" id="custIndustry" name="custIndustry"> <option value="">--请选择--</option>  <option value="${item.dict_id}" <c:if="" test="${item.dict_id == vo.custIndustry}"> selected&gt;${item.dict_item_name }</option>  </select> 
   </div> 
   <div class="form-group"> 
    <label for="custLevel">客户级别</label> 
    <select class="form-control" id="custLevel" name="custLevel"> <option value="">--请选择--</option>  <option value="${ item.dict_id}" <c:if="" test="${ item.dict_id == vo.custLevel }"> selected&gt; ${ item.dict_item_name }</option>  </select> 
   </div> 
   <button type="submit" class="btn btn-primary">查询</button> 
  </form>
```

To deal with the SQL enquiry staments like this:

```sql
SELECT
    *
FROM base_dict
WHERE dict_item_code = '001';
```

### 5.2 Implement the development of DAO

Adding the entities files `BaseDict.java`, `Customer.java`, `QueryVo.java` under `com.lightwing.crm.pojo`

#### 5.2.1 pojo

Keep the column name as same as database's table, so the dao file should look like this

```java
package com.lightwing.crm.pojo;

/**
 * 字典数据模型
 *
 * @author Lightwing Ng
 */
public class BaseDict {
    private String dict_id;
    private String dict_type_code;
    private String dict_type_name;
    private String dict_item_name;
    private String dict_item_code;
    private Integer dict_sort;
    private String dict_enable;
    private String dict_memo;

    public String getDict_id() {
        return dict_id;
    }

    public void setDict_id(String dict_id) {
        this.dict_id = dict_id;
    }

    public String getDict_type_code() {
        return dict_type_code;
    }

    public void setDict_type_code(String dict_type_code) {
        this.dict_type_code = dict_type_code;
    }

    public String getDict_type_name() {
        return dict_type_name;
    }

    public void setDict_type_name(String dict_type_name) {
        this.dict_type_name = dict_type_name;
    }

    public String getDict_item_name() {
        return dict_item_name;
    }

    public void setDict_item_name(String dict_item_name) {
        this.dict_item_name = dict_item_name;
    }

    public String getDict_item_code() {
        return dict_item_code;
    }

    public void setDict_item_code(String dict_item_code) {
        this.dict_item_code = dict_item_code;
    }

    public Integer getDict_sort() {
        return dict_sort;
    }

    public void setDict_sort(Integer dict_sort) {
        this.dict_sort = dict_sort;
    }

    public String getDict_enable() {
        return dict_enable;
    }

    public void setDict_enable(String dict_enable) {
        this.dict_enable = dict_enable;
    }

    public String getDict_memo() {
        return dict_memo;
    }

    public void setDict_memo(String dict_memo) {
        this.dict_memo = dict_memo;
    }
}
```

#### 5.2.2 Mapper

And the corresponding mapper `BaseDictMapper.java` under `crm/mapper/` should look like this:

```java
package com.lightwing.crm.mapper;

import com.lightwing.crm.pojo.BaseDict;

import java.util.List;

/**
 * 字典数据表持久化接口
 *
 * @author Lightwing Ng
 */
public interface BaseDictMapper {
    /**
     * 跟据字典编码查询字典列表
     *
     * @param code
     * @return
     */
    List<BaseDict> getBaseDictByCode(String code);
}
```

#### 5.2.3 `BaseDictMapper.xml`

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.lightwing.crm.mapper.BaseDictMapper">
    <select id="getBaseDictByCode" parameterType="string" resultType="basedict">
        SELECT
            `dict_id`,
            `dict_type_code`,
            `dict_type_name`,
            `dict_item_name`,
            `dict_item_code`,
            `dict_sort`,
            `dict_enable`,
            `dict_memo`
        FROM `base_dict`
        WHERE dict_type_code = #{ code }
    </select>
</mapper>
```

### 5.3 Service Development

#### 5.3.1 The interface of BaseDictService

```java
package com.lightwing.crm.service;

import com.lightwing.crm.pojo.BaseDict;

import java.util.List;

/**
 * 字典数据表业务逻辑接口
 *
 * @author Lightwing Ng
 */
public interface BaseDictService {
    /**
     * 跟据字典编码查询字典列表
     *
     * @param code
     * @return
     */
    List<BaseDict> getBaseDictByCode(String code);
}
```

#### 5.3.2 BaseDictServiceImpl.java

```java
package com.lightwing.crm.service.impl;

import com.lightwing.crm.mapper.BaseDictMapper;
import com.lightwing.crm.pojo.BaseDict;
import com.lightwing.crm.service.BaseDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BaseDictServiceImpl implements BaseDictService {
    @Autowired
    private BaseDictMapper baseDictMapper;

    @Override
    public List<BaseDict> getBaseDictByCode(String code) {
        return baseDictMapper.getBaseDictByCode(code);
    }
}
```

### 5.4 Development of Controller

#### 5.4.1 Modified the previous controller

```java
@Controller
@RequestMapping("customer")
public class CustomerController {
    @Autowired
    private BaseDictService dictService;
    @Autowired
    private CustomerService customerService;
    @Value("${customer_from_type}")
    private String customer_from_type;
    @Value("${customer_industry_type}")
    private String customer_industry_type;
    @Value("${customer_level_type}")
    private String customer_level_type;

    @RequestMapping("list")
    public String list(Model model, QueryVo vo) {

        // 查询来源
        List<BaseDict> fromType = dictService.getBaseDictByCode(customer_from_type);
        // 查询行业
        List<BaseDict> industryType = dictService.getBaseDictByCode(customer_industry_type);
        // 查询级别
        List<BaseDict> levelType = dictService.getBaseDictByCode(customer_level_type);

        // 设置数据模型返回
        model.addAttribute("fromType", fromType);
        model.addAttribute("industryType", industryType);
        model.addAttribute("levelType", levelType);

        // 跟据查询条件分页查询用户列表
        Page<Customer> page = customerService.getCustomerByQueryVo(vo);

        // 设置分页数返回
        model.addAttribute("page", page);

        // 返回查询条件
        model.addAttribute("vo", vo);

        return "customer";
    }
}
```

#### 5.4.2 Current Effect of the front page

![]()

#### 5.4.3 Hard core coding issues

##### 5.4.3.1 Adding `crm.properties`

Under `WEB-INF/lib/`, adding this file

```properties
# customer source 
customer_from_type=002
# coustomer industry
customer_industry_type=001
# customer level
customer_level_type=006
```

##### 5.4.3.2 Modify the `springmvc.xml`

Adding the follow statement into `springmvc.xml`:

```properties
<context:property-placeholder location="classpath:crm.properties"/>
```

##### 5.4.3.3 Modify the Controller

Adding this to the `CustomerController.java`

```java
@Value("${customer_from_type}")
private String customer_from_type;
@Value("${customer_industry_type}")
private String customer_industry_type;
@Value("${customer_level_type}")
private String customer_level_type;
```

If the `crm.properties` path configuration is correct, IDEA will automatically replace the context in braces into the setting one.

## 6 Customer List Display

6.1 Requirements

Setting a filter to select customers by sorting coming source, industries and customer level, then the jsp source code should be like this,

```jsp
        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-default">
                    <div class="panel-heading">客户信息列表</div>
                    <!-- /.panel-heading -->
                    <table class="table table-bordered table-striped">
                        <thead>
                        <tr>
                            <th>ID</th>
                            <th>客户名称</th>
                            <th>客户来源</th>
                            <th>客户所属行业</th>
                            <th>客户级别</th>
                            <th>固定电话</th>
                            <th>手机</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${page.rows}" var="row">
                            <tr>
                                <td>${ row.cust_id }</td>
                                <td>${ row.cust_name }</td>
                                <td>${ row.cust_source }</td>
                                <td>${ row.cust_industry }</td>
                                <td>${ row.cust_level }</td>
                                <td>${ row.cust_phone }</td>
                                <td>${ row.cust_mobile }</td>
                                <td>
                                    <a href="#" class="btn btn-primary btn-xs" data-toggle="modal"
                                       data-target="#customerEditDialog"
                                       onclick="editCustomer(${row.cust_id})">修改</a>
                                    <a href="#" class="btn btn-danger btn-xs"
                                       onclick="deleteCustomer(${row.cust_id})">删除</a>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                    <div class="col-md-12 text-right">
                        <itcast:page
                                url="${pageContext.request.contextPath }/customer/list.action"/>
                    </div>
                    <!-- /.panel-body -->
                </div>
                <!-- /.panel -->
            </div>
            <!-- /.col-lg-12 -->
        </div>
```

In this condition, for the returning data is Page, then the SQL statement should look like this,

```sql
SELECT
    a.cust_id,
    a.cust_name,
    a.cust_user_id,
    a.cust_create_id,
    b.dict_item_name cust_source,
    c.dict_item_name cust_industry,
    a.cust_linkman,
    a.cust_phone,
    a.cust_mobile,
    a.cust_zipcode,
    a.cust_address,
    a.cust_createtime
FROM customer a
    LEFT JOIN base_dict b ON a.cust_source = b.dict_id
    LEFT JOIN base_dict c ON a.cust_industry = c.dict_id
    LEFT JOIN base_dict d ON a.cust_level = d.dict_id
WHERE
    a.cust_name LIKE '%静%'
AND a.cust_source = '6'
AND a.cust_industry = '2'
AND a.cust_level = '22'
LIMIT 0, 10;
```

And the result should be:

```powershell
+----------+-----------+--------------+----------------+--------------+---------------+-----------------+---------------+--------------------+--------------+-------------------------+---------------------+
| cust_id  | cust_name | cust_user_id | cust_create_id | cust_source  | cust_industry | cust_linkman    | cust_phone    | cust_mobile        | cust_zipcode | cust_address            | cust_createtime     |
+----------+-----------+--------------+----------------+--------------+---------------+-----------------+---------------+--------------------+--------------+-------------------------+---------------------+
| 26408665 | 静香      |         NULL |           NULL | 电话营销     | 电子商务      | Gerardo Holland | (725) 2253554 | +86 (795) 295-0799 | NULL         | 0897 Prairie Rose Plaza | 2018-01-15 00:00:00 |
+----------+-----------+--------------+----------------+--------------+---------------+-----------------+---------------+--------------------+--------------+-------------------------+---------------------+

```

### 6.2 pojo: Customer entity

```java
public class Customer {
    private Long cust_id;
    private String cust_name;
    private Long cust_user_id;
    private Long cust_create_id;
    private String cust_source;
    private String cust_industry;
    private String cust_level;
    private String cust_linkman;
    private String cust_phone;
    private String cust_mobile;
    private String cust_zipcode;
    private String cust_address;
    private Date cust_createtime;  
}
```

### 6.3 implement DAO

There exists two requirements for DAO development:

1. Enquiry the customers' information by selecting conditions;
2. Enquiry the total number on some certain conditions.

#### 6.3.1 QueryVo

Create a Java Class under `/pojo`:

```java
public class QueryVo {
    private String custName;
    private String custSource;
    private String custIndustry;
    private String custLevel;

    // 当前页码数
    private Integer page = 1;
    // 数据库从哪一条数据开始查
    private Integer start;
    // 每页显示数据条数
    private Integer rows = 10;
    
    ...
        
}
```

#### 6.3.2 Mapper

Create `CustomerMapper.java` under `/mapper`:

```java
package com.lightwing.crm.mapper;

import com.lightwing.crm.pojo.Customer;
import com.lightwing.crm.pojo.QueryVo;

import java.util.List;

public interface CustomerMapper {
    List<Customer> getCustomerByQueryVo(QueryVo vo);

    Integer getCountByQueryVo(QueryVo vo);

    Customer getCustomerById(Integer id);

    void updateCustomer(Customer customer);

    void deleteCustomer(Integer id);
}
```

#### 6.3.3 Mapper.xml

And under the same directory, create `CustomerMapper.xml`, and coding like this:

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.lightwing.crm.mapper.CustomerMapper">

    <sql id="customer_where">
        <where>
            <if test="custName != null and custName != ''">
                AND c.`cust_name` LIKE '%${ custName }%'
            </if>
            <if test="custSource != null and custSource != ''">
                AND c.`cust_source` = #{ custSource }
            </if>
            <if test="custIndustry != null and custIndustry != ''">
                AND c.`cust_industry` = #{ custIndustry }
            </if>
            <if test="custLevel != null and custLevel != ''">
                AND c.`cust_level` = #{ custLevel }
            </if>
        </where>
    </sql>

    <select id="getCustomerByQueryVo" parameterType="queryvo" resultType="customer">
        SELECT
            c.`cust_id`,
            c.`cust_name`,
            c.`cust_user_id`,
            c.`cust_create_id`,
            s.`dict_item_name` `cust_source`,
            i.`dict_item_name` `cust_industry`,
            l.`dict_item_name` `cust_level`,
            c.`cust_linkman`,
            c.`cust_phone`,
            c.`cust_mobile`,
            c.`cust_zipcode`,
            c.`cust_address`,
            c.`cust_createtime`
        FROM `customer` c
        LEFT JOIN base_dict s ON c.`cust_source`= s.`dict_id`
        LEFT JOIN base_dict i ON c.`cust_industry` = i.`dict_id`
        LEFT JOIN base_dict l ON c.`cust_level` = l.`dict_id`
        <include refid="customer_where"/>
        LIMIT #{ start }, #{ rows };
    </select>

    <select id="getCountByQueryVo" parameterType="queryvo" resultType="int">
        SELECT count(1)
        FROM `customer` c
        LEFT JOIN base_dict s ON c.`cust_source`= s.`dict_id`
        LEFT JOIN base_dict i ON c.`cust_industry` = i.`dict_id`
        LEFT JOIN base_dict l ON c.`cust_level` = l.`dict_id`
        <include refid="customer_where"/>
    </select>

    <select id="getCustomerById" parameterType="int" resultType="customer">
        SELECT
            `cust_id`,
            `cust_name`,
            `cust_user_id`,
            `cust_create_id`,
            `cust_source`,
            `cust_industry`,
            `cust_level`,
            `cust_linkman`,
            `cust_phone`,
            `cust_mobile`,
            `cust_zipcode`,
            `cust_address`,
            `cust_createtime`
        FROM `customer`
        WHERE cust_id = #{ id }
    </select>

    <update id="updateCustomer" parameterType="customer">
        UPDATE `customer`
        <set>
            <if test="cust_name != null">
                `cust_name` = #{ cust_name },
            </if>
            <if test="cust_source != null and cust_source != ''">
                `cust_source` = #{ cust_source },
            </if>
            <if test="cust_industry != null and cust_industry != ''">
                `cust_industry` = #{ cust_industry },
            </if>
            <if test="cust_level != null and cust_level != ''">
                `cust_level` = #{ cust_level },
            </if>
            <if test="cust_linkman != null and cust_linkman != ''">
                `cust_linkman` = #{ cust_linkman },
            </if>
            <if test="cust_phone != null and cust_phone != ''">
                `cust_phone` = #{ cust_phone },
            </if>
            <if test="cust_mobile != null and cust_mobile != ''">
                `cust_mobile` = #{ cust_mobile },
            </if>
            <if test="cust_zipcode != null and cust_zipcode != ''">
                `cust_zipcode` = #{ cust_zipcode },
            </if>
            <if test="cust_address != null and cust_address != ''">
                `cust_address` = #{ cust_address },
            </if>
        </set>
        WHERE `cust_id` = #{ cust_id };
    </update>

    <delete id="deleteCustomer" parameterType="int">
        DELETE
        FROM `customer`
        WHERE `cust_id` = #{ cust_id };
    </delete>
</mapper>
```

### 6.4 Implement Service

#### 6.4.1 Interface of enquiry

Creating an interface class `CustomerService.java` under `service/`:

```java
package com.lightwing.crm.service;

import com.lightwing.crm.pojo.Customer;
import com.lightwing.crm.pojo.QueryVo;
import com.lightwing.crm.utils.Page;

/**
 * 客户信息业务逻辑接口
 *
 * @author Lightwing Ng
 */
public interface CustomerService {

    /**
     * 查询查询条件，分页查询用户列表
     *
     * @param vo
     * @return
     */
    Page<Customer> getCustomerByQueryVo(QueryVo vo);


    /**
     * 跟据id查询用户信息
     *
     * @param id
     * @return
     */
    Customer getCustomerById(Integer id);

    /**
     * 更新用户信息
     *
     * @param customer
     */
    void updateCustomer(Customer customer);

    /**
     * 删除用户信息
     *
     * @param id
     */
    void deleteCustomer(Integer id);
}
```

#### 6.4.2 Implement of enquiry

Then, create a implement class `CustomerServiceImpl.java` under `/service/impl`:

```java
@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerMapper customerMapper;

    @Override
    public Page<Customer> getCustomerByQueryVo(QueryVo vo) {
        // 计算分页查询从哪条记录开始
        vo.setStart((vo.getPage() - 1) * vo.getRows());

        // 查询总记录数
        Integer total = customerMapper.getCountByQueryVo(vo);

        // 查询每页的数据列表
        List<Customer> list = customerMapper.getCustomerByQueryVo(vo);

        // 包装分页数据
        Page<Customer> page = new Page<Customer>(total, vo.getPage(), vo.getRows(), list);

        return page;
    }
}
```

### 6.5 Implement Controller

Now, the Controller should look like:

```java
@Controller
@RequestMapping("customer")
public class CustomerController {
    @Autowired
    private BaseDictService dictService;
    @Autowired
    private CustomerService customerService;
    @Value("${customer_from_type}")
    private String customer_from_type;
    @Value("${customer_industry_type}")
    private String customer_industry_type;
    @Value("${customer_level_type}")
    private String customer_level_type;

    @RequestMapping("list")
    public String list(Model model, QueryVo vo) {

        // 查询来源
        List<BaseDict> fromType = dictService.getBaseDictByCode(customer_from_type);
        // 查询行业
        List<BaseDict> industryType = dictService.getBaseDictByCode(customer_industry_type);
        // 查询级别
        List<BaseDict> levelType = dictService.getBaseDictByCode(customer_level_type);

        // 设置数据模型返回
        model.addAttribute("fromType", fromType);
        model.addAttribute("industryType", industryType);
        model.addAttribute("levelType", levelType);

        // 跟据查询条件分页查询用户列表
        Page<Customer> page = customerService.getCustomerByQueryVo(vo);

        // 设置分页数返回
        model.addAttribute("page", page);

        // 返回查询条件
        model.addAttribute("vo", vo);

        return "customer";
    }
}
```

## 7 Module: Update Customers' Information

### 7.1 Requirement

![]()

1. Click "Modify" button, and a new customer entity will be initialized and return with a new dialog window for user to type some information;
2. Click "Save" and then all the information input will be saved up to database.

### 7.2 Data Echo Implement

![]()

Under the `customer.jsp`, adding a JavaScript function within `<body></body>` scope:

```jsp
<script type="text/javascript">
    function editCustomer(id) {
        $.ajax({
            type: "get",
            url: "<%=basePath%>customer/edit.action",
            data: {"id": id},
            success: function (data) {
                $("#edit_cust_id").val(data.cust_id);
                $("#edit_customerName").val(data.cust_name);
                $("#edit_customerFrom").val(data.cust_source)
                $("#edit_custIndustry").val(data.cust_industry)
                $("#edit_custLevel").val(data.cust_level)
                $("#edit_linkMan").val(data.cust_linkman);
                $("#edit_phone").val(data.cust_phone);
                $("#edit_mobile").val(data.cust_mobile);
                $("#edit_zipcode").val(data.cust_zipcode);
                $("#edit_address").val(data.cust_address);
            }
        });
    }

    function updateCustomer() {
        alert($("#edit_customer_form").serialize());
        $.post("<%=basePath%>customer/update.action", $("#edit_customer_form").serialize(), function (data) {
            if (data == "0") {
                alert("客户信息更新成功！");
            } else {
                alert("客户信息更新失败！");
            }
            window.location.reload();
        });
    }

    function deleteCustomer(id) {
        if (confirm('确实要删除该客户吗?')) {
            $.post("<%=basePath%>customer/delete.action", {"id": id}, function (data) {
                if (data == "0") {
                    alert("客户信息删除成功！");
                } else {
                    alert("客户信息删除失败！");
                }
                window.location.reload();
            });
        }
    }
</script>
```

This is a AJAX request to ask us to get back-end development done, returning data to the front-end;

### 7.3 Echo Functino Implement

#### 7.3.1 Mapper Interface

In `CustomerMapper.java`, adding the following code segment:

```java
Customer getCustomerById(Integer id);
```

#### 7.3.2 Mapper.xml

Adding SQL statement into `CustomerMapper.xml`:

```sql
<select id="getCountByQueryVo" parameterType="queryvo" resultType="int">
	SELECT count(1)
	FROM `customer` c
	LEFT JOIN base_dict s ON c.`cust_source`= s.`dict_id`
	LEFT JOIN base_dict i ON c.`cust_industry` = i.`dict_id`
	LEFT JOIN base_dict l ON c.`cust_level` = l.`dict_id`
	<include refid="customer_where"/>
</select>
```

#### 7.3.3 Service Interface

Adding following code segment into `CustomerService.java`:

```java
Customer getCustomerById(Integer id);
```

#### 7.3.4 Service Interface Implement Class

Override with this code segment into `impl/CustomerServiceImpl.java`:

```java
@Override
public Customer getCustomerById(Integer id) {
	return customerMapper.getCustomerById(id);
}
```

#### 7.3.5 Controller

Implement Method in `CustomerController.java`, adding mapping:

```java
@RequestMapping("edit")
@ResponseBody
public Customer edit(Integer id) {
	Customer customer = customerService.getCustomerById(id);
	return customer;
}
```

### 7.4 Editing Customer Data

#### 7.4.1 Requirement

![]()

#### 7.4.2 JavaScript in `customer.jsp`

```javascript
function updateCustomer() {
	alert($("#edit_customer_form").serialize());
	$.post("<%=basePath%>customer/update.action", $("#edit_customer_form").serialize(), function (data) {
	if (data == "0") {
		alert("客户信息更新成功！");
	} else {
		alert("客户信息更新失败！");
	}
		window.location.reload();
	});
}
```

### 7.5 Implement of Editing Fuction

#### 7.5.1 Mapper Interface

Adding interface in `CustomerMapper.java`:

```java
void updateCustomer(Customer customer);
```

#### 7.5.2 `Mapper.xml` implement

Adding SQL Statement in `CustomerMapper.xml`,

```jsp
    <update id="updateCustomer" parameterType="customer">
        UPDATE `customer`
        <set>
            <if test="cust_name != null">
                `cust_name` = #{ cust_name },
            </if>
            <if test="cust_source != null and cust_source != ''">
                `cust_source` = #{ cust_source },
            </if>
            <if test="cust_industry != null and cust_industry != ''">
                `cust_industry` = #{ cust_industry },
            </if>
            <if test="cust_level != null and cust_level != ''">
                `cust_level` = #{ cust_level },
            </if>
            <if test="cust_linkman != null and cust_linkman != ''">
                `cust_linkman` = #{ cust_linkman },
            </if>
            <if test="cust_phone != null and cust_phone != ''">
                `cust_phone` = #{ cust_phone },
            </if>
            <if test="cust_mobile != null and cust_mobile != ''">
                `cust_mobile` = #{ cust_mobile },
            </if>
            <if test="cust_zipcode != null and cust_zipcode != ''">
                `cust_zipcode` = #{ cust_zipcode },
            </if>
            <if test="cust_address != null and cust_address != ''">
                `cust_address` = #{ cust_address },
            </if>
        </set>
        WHERE `cust_id` = #{ cust_id };
    </update>
```

#### 7.5.3 Service Interface

Adding interface in `CustomerService.java`:

```java
void updateCustomer(Customer customer);
```

#### 7.5.4 Service Interfacle Class

Final, the function scope should look like:

```java
@Override
public void updateCustomer(Customer customer) {
	customerMapper.updateCustomer(customer);
}
```

#### 7.5.5 Controller

Adding annotations in `CustomerController.java`

```java
@RequestMapping("update")
@ResponseBody
public String update(Customer customer) {
    String msg = "1";
    try {
        customerService.updateCustomer(customer);
        msg = "0";
    } catch (Exception e) {
        e.printStackTrace();
    }
    return msg;
}
```

## 8 Customer Deleting

### 8.1 Requirement Analysis

When user try to delete a customer's information, pop up an alert, saying:

![]()

The JSP should look like:

```javascript
function deleteCustomer(id) {
    if (confirm('确实要删除该客户吗?')) {
        $.post("<%=basePath%>customer/delete.action", {"id": id}, function(data) {
            if (data == "0")
                alert("客户信息删除成功！");
            else
                alert("客户信息删除失败！");
            window.location.reload();
        });
    }
}
```

### 8.2 Function Implement

#### 8.2.1 Mapper Interface

Adding fellow code segment into `CustomerMapper.java`:

```java
void deleteCustomer(Integer id);
```

#### 8.2.2 Mapping

Addig SQL Statement into `CustomerMapper.xml`,

```sql
<delete id="deleteCustomer" parameterType="int">
	DELETE
	FROM `customer`
	WHERE `cust_id` = #{ cust_id };
</delete>
```

#### 8.2.5 Controller

Back to `CustomerController.java`, let's add the method of deleting code segment:

```java
@RequestMapping("delete")
@ResponseBody
public String delete (Integer id) {
    String msg = "1";
    try {
        customerService.deleteCustomer(id);
        msg = "0";
    } catch (Exception e) {
        e.printStackTrace();
    }
    return msg;
}
```