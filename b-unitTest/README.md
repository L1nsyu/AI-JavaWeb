# 单元测试

## 一.概述
![1.png](showPic/1.png)![2.png](showPic/2.png)![3.png](showPic/3.png)![4.png](showPic/4.png)![5.png](showPic/5.png)![6.png](showPic/6.png)
![7.png](showPic/7.png)![8.png](showPic/8.png)

### 使用JUnit，对Userservice中业务方法进行单元测试。
1. 在pom.xml中，引入JUnit的依赖<br>
2. 在test/java目录下，创建测试类，并编写对应的测试方法，并在方法上声明@Test注解。<br>
3. 运行单元测试(测试通过:绿色;测试失败:红色)。<br>

**注意:<br>
JUnit单元测试类名命名规范为:XxxxxTest【规范】。<br>
Junit单元测试的方法，必须声明为 public void【规定】**

## 二.常见注解
![9.png](showPic/9.png)![10.png](showPic/10.png)

## 三.断言
![11.png](showPic/11.png)![12.png](showPic/12.png)![13.png](showPic/13.png)

**在JUnit单元测试中，为什么要使用断言?<br>
小结:<br>
单元测试方法运行不报错，不代表业务方法没问题，
通过断言可以检测方法运行结果是否和预期一致，从而判断业务方法的正确性.<br>
Assertions.assertXxxx(...)**

## 四.企业开发规范
![14.png](showPic/14.png)![15.png](showPic/15.png)

## 五.依赖范围
**依赖的jar包，默认情况下，可以在任何地方使用。可以通过`<scope>...</scope>`设置其作用范围。**
![16.png](showPic/16.png)![17.png](showPic/17.png)![18.png](showPic/18.png)


