package com.jomkie.dto;

import com.jomkie.annotations.DateTimeValid;
import com.jomkie.common.PreBuildParamDto;
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
public class JoTestDto implements PreBuildParamDto {

    private String id;

    @DateTimeValid(value = DateTimeValid.Format.DATE_FORMAT , message = "开始时间格式有误")
    private String startTime;

    @DateTimeValid(value = DateTimeValid.Format.DATE_FORMAT , message = "结束时间格式有误")
    private String endTime;

    @Override
    public void buildActualParam() {}

}
