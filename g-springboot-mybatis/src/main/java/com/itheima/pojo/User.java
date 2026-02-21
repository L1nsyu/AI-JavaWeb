package com.itheima.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


//      注解	                     作用
//@Data	                自动生成所有字段的 getter、setter，以及 toString(), equals(), hashCode() 方法.
//@AllArgsConstructor	自动生成一个包含所有字段的构造函数.
//@NoArgsConstructor	自动生成一个无参数的构造函数.


@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Integer id;
    private String username;
    private String password;
    private String name;
    private Integer age;
}
