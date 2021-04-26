package com.jomkie.dto;

import com.jomkie.annotations.DateTimeValid;
import com.jomkie.aop.valid.NeedValidating;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Jomkie
 * @since 2021-04-26 9:42:40
 * 测试对象dto
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@NeedValidating
public class JoTestDto {

    private String id;

    @DateTimeValid(value = DateTimeValid.Format.DATE_TIME_FORMAT , message = "开始时间格式有误")
    private String startTime;

    @DateTimeValid(value = DateTimeValid.Format.DATE_TIME_FORMAT , message = "结束时间格式有误")
    private String endTime;

}
