package com.jomkie.common.entity.bean;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JoUser {
	
	private Integer id;
	private String name;
	private Integer age;
	private String address;
	private Date birthday;
	
}
