package com.jomkie.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jomkie.annotations.user.UserGroup;
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
@TableName(JoUser.TABLE_NAME)
public class JoUser {

    public static final String TABLE_NAME = "jo_user";

    @NotNull(message = "ID不能为空", groups = {UserGroup.UserUpdate.class, UserGroup.UserDel.class})
    private Long id;

    @NotEmpty(message = "姓名不能为空", groups = {UserGroup.UserAdd.class, UserGroup.UserUpdate.class})
    private String name;

    @NotNull(message = "年龄不能为空", groups = {UserGroup.UserAdd.class, UserGroup.UserUpdate.class})
    @Range(min = 0, max = 200, message = "年龄范围必须在 {min} - {max} 之间", groups = {UserGroup.UserAdd.class, UserGroup.UserUpdate.class, UserGroup.UserDel.class})
    private Integer age;

    @Pattern(regexp = "^.+@.+$", message = "邮箱不合法", groups = {UserGroup.UserAdd.class, UserGroup.UserUpdate.class})
    private String email;

}
