package com.jomkie.common.entity.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.jomkie.common.entity.vo.JoKongfuVO;
import com.jomkie.common.entity.vo.JouserVO;

import lombok.Data;

@Data
public class JoUserAccountDTO {
	
	private Integer userId;
	
	private BigDecimal money;
	
	private Date createTime;
	
}