package com.jomkie.common.entity.dto;

import java.util.Date;

import lombok.Data;

@Data
public class JoUserDTO {
	
	private Integer id;
	private String name;
	private Integer age;
	private String address;
	private Date birthday;
	
}
