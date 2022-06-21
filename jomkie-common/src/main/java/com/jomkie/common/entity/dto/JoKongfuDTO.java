package com.jomkie.common.entity.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class JoKongfuDTO {

    private Integer userId;
    private BigDecimal power;
    private Integer level;
    private Date createTime;
    private JoUserDTO joUserDTO;
    private List<JoUserDTO> joUserList;
	
}
