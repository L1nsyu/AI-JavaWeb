package com.itheima;

import com.itheima.mapper.UserMapper;
import com.itheima.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
//SpringBootTest单元测试的注解 - 当前测试类中的测试方法运行时,会启动springboot项目 - IOC容器
class GSpringbootMybatisApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testFindAll() {
        List<User> userList = userMapper.findAll();
        userList.forEach(System.out::println);
    }

/*
    @Test
    public void testDeleteById(){
        userMapper.deleteById(5);
        //因为不需要返回值,直接调用即可
    }
*/

    /*
    * 测试通过ID查询用户
    * */
    @Test
    public void testFindById() {
        Integer i = userMapper.deleteById(5);
        System.out.println("执行完毕,影响的记录数为:" + i);
    }


    /*
    * 测试新增
    * */
    @Test
    public void testInsert(){
        User user = new User(null,"zhengtaikao","123456","郑凯涛",22);
        userMapper.insert(user);
    }


    /*
    * 测试更新
    * */
    @Test
    public void testUpdate(){
        User user = new User(1,"zhouyu","123456","周瑜",20);
        userMapper.update(user);
    }


    /*
    * 测试查询
    * */
    @Test
    public void testFindByUsernameAndPassword(){
        User user = userMapper.findByUsernameAndPassword("zhouyu", "6688");
        System.out.println(user);
    }
}
