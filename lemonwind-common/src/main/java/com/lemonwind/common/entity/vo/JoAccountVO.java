package com.lemonwind.common.entity.vo;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class JoAccountVO {
	
    private Integer userId;
    private BigDecimal money;
    private String createTime;
	
}
