package com.jomkie.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Jomkie
 * @date 2021/3/28 21:06:56
 * 用户视图层
 */
@Data
@NoArgsConstructor
public class JoUserDto {

    private Long id;

    private String name;

    private Integer age;

    private String email;

}
