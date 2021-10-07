package com.jomkie.common.entity.bean;

import java.util.Date;

import lombok.Data;

@Data
public class JoUser {
	
	private Integer id;
	private String name;
	private Integer age;
	private String address;
	private Date birthday;
	
}
