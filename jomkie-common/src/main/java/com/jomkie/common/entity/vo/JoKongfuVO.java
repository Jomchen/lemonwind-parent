package com.jomkie.common.entity.vo;


import lombok.Data;

@Data
public class JoKongfuVO {

    private Integer userId;
    private String power;
    private Integer level;
    private String createTime;
    private JouserVO joUserVO;
    private Integer joUserAmount;
	
}
