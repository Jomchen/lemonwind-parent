package com.jomkie.common.entity.bean;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

@Data
public class JoAccount {

	private Integer userId;
	
	private BigDecimal money;
	
	private Date createTime;
	
}
