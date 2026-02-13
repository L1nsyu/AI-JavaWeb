package com.itheima;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/*
 * 测试类
 * */

//三.Junit常见注解
@DisplayName("用户信息测试类")
public class UserServiceTest {
    @BeforeAll //在所有的单元测试方法运行之前, 运行一次
    public static void beforeAll() {
        System.out.println("before All");
    }

    @AfterAll //在所有的单元测试方法运行之后, 运行一次
    public static void afterAll() {
        System.out.println("after All");
    }

    @BeforeEach //在每一个单元测试方法运行之前, 都会运行一次
    public void beforeEach() {
        System.out.println("before Each");
    }

    @AfterEach //在每一个单元测试方法运行之后, 都会运行一次
    public void afterEach() {
        System.out.println("after Each");
    }

    //一.使用JUnit，对 Userservice 中业务方法进行单元测试。
    @Test
    public void testGetAge() {
        UserService userService = new UserService();
        Integer age = userService.getAge("100000200010011011");
        System.out.println(age);
    }

    //二.断言
    @Test
    public void testGenderWithAssert() {
        UserService userService = new UserService();
        String gender = userService.getGender("100000200010011011");
        //断言
        Assertions.assertEquals("男", gender, "性别获取错误有问题");
    }

    @Test
    public void testGenderWithAssert2() {
        UserService userService = new UserService();
        //断言
        Assertions.assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                userService.getGender(null);
            }
        });
    }


    //三.Junit常见注解
    /*
     * 参数化测试
     * */
    @DisplayName("测试用户性别")
    @ParameterizedTest
    @ValueSource(strings = {"100000200010011011", "100000200010011031", "100000200010011051"})
    public void testGetGender2(String idCard) {
        UserService userService = new UserService();
        String gender = userService.getGender(idCard);
        //断言
        Assertions.assertEquals("男", gender);
    }
}
