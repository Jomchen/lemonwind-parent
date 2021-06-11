package com.jomkie.model;

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

    private Long id;

    private String name;

    private Integer age;

    private String email;

    private Date birthday;

}
