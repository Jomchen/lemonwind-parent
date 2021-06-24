package com.jomkie.dto;

import com.jomkie.annotations.DateTimeValid;
import com.jomkie.annotations.user.UserGroup;
import com.jomkie.common.PreBuildParamDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Jomkie
 * @since 2021/3/28 21:06:56
 * 用户视图层
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JoUserDto implements PreBuildParamDto {

    @NotNull(message = "ID不能为空", groups = {UserGroup.UserUpdate.class, UserGroup.UserDel.class})
    private Long id;

    @NotEmpty(message = "姓名不能为空", groups = {UserGroup.UserAdd.class, UserGroup.UserUpdate.class})
    private String name;

    @NotNull(message = "年龄不能为空", groups = {UserGroup.UserAdd.class, UserGroup.UserUpdate.class})
    @Range(min = 0, max = 200, message = "年龄范围必须在 {min} - {max} 之间", groups = {UserGroup.UserAdd.class, UserGroup.UserUpdate.class, UserGroup.UserDel.class})
    private Integer age;

    @NotNull(message = "性别不能为空", groups = {UserGroup.UserAdd.class, UserGroup.UserUpdate.class})
    @Range(min = 0, max = 1, message = "性别传值为 {min} 或 {max}")
    private Short sex;

    @Pattern(regexp = "^.+@.+$", message = "邮箱不合法", groups = {UserGroup.UserAdd.class, UserGroup.UserUpdate.class})
    private String email;

    @DateTimeValid(value = DateTimeValid.Format.DATE_FORMAT , message = "时间格式有误")
    @NotNull(message = "时间不能为空")
    private String dateTime;

    private String testBuildParamData;

    @Override
    public void buildActualParam() {
        testBuildParamData = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

}
