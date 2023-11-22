package com.lemonwind.datastructure.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 测试用户
 * @author Jomkie
 * @since 2021-06-11 10:0:10
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestUser {

    /** ID */
    private Long id;

    /** 姓名 */
    private String name;

    /** 年龄 */
    private Integer age;

    /** 邮箱 */
    private String email;

    /** 生日 */
    private Date birthday;

}
