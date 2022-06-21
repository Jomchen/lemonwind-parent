package com.jomkie.common.entity.dto;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

@Data
public class JoUserAccountDTO {
	
    private Integer userId;
    private BigDecimal money;
    private Date createTime;
	
}
