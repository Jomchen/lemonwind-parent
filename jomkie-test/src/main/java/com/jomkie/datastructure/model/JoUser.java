package com.jomkie.datastructure.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Jomkie
 * @since 2021/3/28 21:07:11
 * 用户模型层
 */
@Data
@TableName(JoUser.TABLE_NAME)
public class JoUser {

    public static final String TABLE_NAME = "jo_user";

    private Long id;

    private String name;

    private Integer age;

    private Short sex;

    private String email;

    public JoUser() {

    }

    public JoUser(Long id, String name, Integer age, Short sex, String email) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.email = email;
    }

}
