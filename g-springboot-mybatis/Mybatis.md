# Mybatis

---
# 代码报错的情况:
1. 为什么会出现这个警告？
   <br>背景：你正在使用的测试框架 Mockito（特别是它的 mock-maker-inline 功能，用于模拟静态方法或 final 类）需要修改正在运行的 Java 虚拟机的字节码。
   <br>原因：为了实现这种修改，Mockito 使用了一个叫 Byte Buddy 的代理（Agent）并试图动态地把自己“挂载”到 JVM 上。
   <br>JDK 的变化：从 JDK 21 开始，Java 官方为了提高安全性，决定默认禁止这种“动态加载代理”的行为。目前的版本只是发出警告（WARNING），告诉你“这种操作在未来的 Java 版本中将被完全禁止，请尽快修改配置”。
2. 这是什么？ 这不是错误 (Error)，而是 警告 (Warning)。
   <br>是否影响结果？ 完全不影响。你的测试结果（通过或失败）是真实有效的，代码逻辑也是正常执行的。
3. 如果想消除这个报错.

        <!-- 👇👇👇 新增部分：添加 maven-surefire-plugin 配置 👇👇👇 -->
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-surefire-plugin</artifactId>
                <configuration>
                    <argLine>-XX:+EnableDynamicAgentLoading -Xshare:off</argLine>
                </configuration>
            </plugin>
            <!-- 👆👆👆 新增结束 👆👆👆 -->

## 01.入门程序

**准备工作:**<br>
1.创建SpringBoot工程、引入Mybatis相关依赖.<br>
2.准备数据库表user、实体类User.<br>
3.配置Mybatis(在application.properties中数据库连接信息).<br>
**编写Mybatis程序:** 编写Mybatis的持久层接口，定义SQL(注解/XML).<br>

![img.png](showPic/img.png)![img_1.png](showPic/img_1.png)![img_2.png](showPic/img_2.png)![img_3.png](showPic/img_3.png)

@SpringBootTest 注解:<br>
一句话概括：<br>
它会启动并加载你完整的 Spring Boot 应用程序，以便进行“集成测试”。<br>
您可以把它想象成：“试驾一辆完全组装好的汽车”，而不是只测试一个单独的零件（那是单元测试做的事）。

1. 加载完整环境 (Loads the Application Context)<br>
   它会查找你的主配置类（通常是带 @SpringBootApplication 的那个），然后启动一个完整的 Spring 容器。这意味着你项目里所有的
   Bean (@Service, @Repository, @Component 等) 都会被创建并注入，就像你正常运行程序一样。
2. 专为集成测试设计 (For Integration Tests)<br>
   它的目的是测试不同组件之间协作是否正常。比如，测试一个 Controller 调用 Service，Service 再调用 Repository 与数据库交互的完整流程。
3. 支持依赖注入 (Supports Dependency Injection)<br>
   在测试类中，你可以直接使用 @Autowired 来注入应用里的任何 Bean，然后在测试方法中直接调用它的功能。
   ---

| 特性       | @SpringBootTest (集成测试) | 单元测试 (Unit Test)                  |
|:---------|:-----------------------|:----------------------------------|
| **目标**   | 测试整个应用或多层组件的协作         | 只测试一个孤立的类或方法                      |
| **环境**   | 加载**完整**的 Spring 环境    | **不加载** Spring 环境，通常用 Mock 对象替代依赖 |
| **速度**   | **慢** (因为要启动整个应用)      | **极快**                            |
| **何时使用** | 当你需要测试端到端的功能或确保各层配合无误时 | 当你只想验证某个类的内部逻辑是否正确时               |

## 02.辅助配置 & JDBC VS Mybatis

![img_4.png](showPic/img_4.png)![img_5.png](showPic/img_5.png)![img_6.png](showPic/img_6.png)
![img_7.png](showPic/img_7.png)![img_8.png](showPic/img_8.png)![img_9.png](showPic/img_9.png)

## 03.数据库连接池

1. 没有数据库连接池的情况：创建连接后，用完会释放
   ![img_10.png](showPic/img_10.png)![img_11.png](showPic/img_11.png)
2. 有数据库连接池的情况：在连接池使用现有的数据库连接，用完再还回去
   ![img_12.png](showPic/img_12.png)![img_13.png](showPic/img_13.png)![img_14.png](showPic/img_14.png)
3. 如果有数据库连接在等待状态下，未能及时归还，会导致数据库连接遗漏的情况，而数据库连接池会解决这个问题。<br>
   当数据库连接超过预设的最长时间时，数据库连接会被强制收回。
   ![img_15.png](showPic/img_15.png)![img_16.png](showPic/img_16.png)
4. 总结：
   ![img_17.png](showPic/img_17.png)

---
![img_18.png](showPic/img_18.png)
Spring Boot 会自动读取配置文件，生成一个 DataSource 对象放到容器里。你直接 @Autowired 拿来用就行。
<br>eg:<br>

    @Service
    public class UserService {
    // 1. 注入 DataSource (这里实际上注入的是 HikariDataSource 或 DruidDataSource)
    @Autowired
    private DataSource dataSource;

    public void testConnection() throws SQLException {
        // 2. 向连接池“借”一个连接
        Connection conn = dataSource.getConnection();

        System.out.println("拿到连接了：" + conn);

        // 3. 使用连接 (做增删改查)...

        // 4. 归还连接 (注意：这里的 close 不是销毁，而是还回池子里)
        conn.close(); 
        }
    }

<br>

总结:
1. DataSource 是个接口，代表连接池。
2. HikariCP / Druid 是它的具体实现。
3. 使用方法：在配置文件填好账号密码 -> @Autowired 注入 -> .getConnection() 获取连接。

![img_19.png](showPic/img_19.png)![img_20.png](showPic/img_20.png)

## 04.增删查改-删除操作
![img_21.png](showPic/img_21.png)![img_22.png](showPic/img_22.png)![img_23.png](showPic/img_23.png)

## 05.增删查改-新增操作
![img_24.png](showPic/img_24.png)

## 06.增删查改-更新操作
![img_25.png](showPic/img_25.png)

## 07.增删查改-查询操作
![img_26.png](showPic/img_26.png)![img_27.png](showPic/img_27.png)![img_28.png](showPic/img_28.png)
为了不记复杂的规则，建议遵循以下简单标准：
1. 一个参数（基础类型，如 int, String）：可以不加 @Param，MyBatis 能自动识别。
2. 一个参数（对象类型，如 User user）：不用加 @Param，SQL 里直接用 #{属性名} 即可。
3. 多个参数（最重要的！）：必须加 @Param。只要参数大于等于 2 个，就给每个参数都加上 @Param，这样永远不会出错，代码也更清晰。
![img_29.png](showPic/img_29.png)

## 08.XML映射配置
XML文件的基本结构:

    <?xml version="1.0" encoding="UTF-8" ?>
    <!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
    <mapper namespace="（根据自身填）">

    </mapper>

![img_30.png](showPic/img_30.png)![img_31.png](showPic/img_31.png)
![img_32.png](showPic/img_32.png)![img_33.png](showPic/img_33.png)
![img_34.png](showPic/img_34.png)![img_35.png](showPic/img_35.png)![img_36.png](showPic/img_36.png)







