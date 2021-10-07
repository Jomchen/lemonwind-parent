package com.jomkie.test.mapstruct;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import com.jomkie.common.entity.dto.JoKongfuDTO;
import com.jomkie.common.entity.vo.JoKongfuVO;

@Mapper(componentModel = "spring")
public abstract class JoKongfuConverter {

	public JoKongfuConverter INSTANCE = Mappers.getMapper(JoKongfuConverter.class);
	
	/** 还有对象转换，不同字段名转换，勾子处理 */
	@Mappings(
			value = {
				@Mapping(source = "power", target = "power", numberFormat = "#.00"),
				@Mapping(source = "createTime", target = "createTime", dateFormat = "yyyy-MM-dd HH:mm:ss")
			}
	)
	public abstract JoKongfuVO dtoToVo(JoKongfuDTO dto);
	
	
	
	
}
