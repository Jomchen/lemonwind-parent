package com.jomkie.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jomkie.annotations.user.UserGroup;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @author Jomkie
 * @date 2021/3/28 21:07:11
 * 用户模型层
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(JoUser.TABLE_NAME)
public class JoUser {

    public static final String TABLE_NAME = "jo_user";

    private Long id;

    private String name;

    private Integer age;

    private String email;

}
