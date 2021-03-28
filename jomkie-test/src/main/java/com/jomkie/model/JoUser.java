package com.jomkie.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Jomkie
 * @date 2021/3/28 21:07:11
 * 用户模型层
 */
@Data
@NoArgsConstructor
@TableName(JoUser.TABLE_NAME)
public class JoUser {

    public static final String TABLE_NAME = "jo_user";

    private Long id;
    private String name;
    private Integer age;
    private String email;

}
