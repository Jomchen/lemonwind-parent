package com.jomkie.dto;

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
public class JoTestDto {

    private String id;

    private String startTime;

    private String endtime;

}
