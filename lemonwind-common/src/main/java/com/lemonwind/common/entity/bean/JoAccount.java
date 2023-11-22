package com.lemonwind.common.entity.bean;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class JoAccount {

	private Integer userId;
	private BigDecimal money;
	private Date createTime;
	
}
