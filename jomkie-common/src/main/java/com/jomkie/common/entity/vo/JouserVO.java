package com.jomkie.common.entity.vo;

import java.util.Date;

import lombok.Data;

@Data
public class JouserVO {
	
    private Integer id;
    private String name;
    private Integer age;
    private String address;
    private Date birthday;
	
}
