package com.itheima.mapper;


import com.itheima.pojo.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper //应用程序在运行时,会自动的为该接口创建一个实现类对象(代理对象),并且会自动将该实现类对象存入IOC容器 - bean
public interface UserMapper {

    /*
     * 查询所有用户
     * */

    //当配置好了XML文件后就不需要接口的声明注解了:
    //@Select("select id,username,password,name,age from user")
    //有返回值的情况:因为Select查询后是有返回值的,所以不能是void
    public List<User> findAll();

    /*
    不需要返回的情况:
    public void findAll();
    */



    /*
     * 根据id删除用户
     * */
    @Delete("delete from user where id = #{id}")
//    public void deleteById(Integer id);
    public Integer deleteById(Integer id);


    /*
     * 新增用户
     * */
    @Insert("insert into user(username,password,name,age) values (#{username},#{password},#{name},#{age})")
    public void insert(User user);


    /*
     * 更新用户
     * */
    @Update("update user set username = #{username},password =#{password},name = #{name},age = #{age} where id =#{id}")
    public void update(User user);


    /*
    * 根据用户名和密码查询用户信息
    * */
    @Select("select * from user where username = #{username} and password = #{password}")
    public User findByUsernameAndPassword(@Param("username") String username, @Param("password") String password);


}
