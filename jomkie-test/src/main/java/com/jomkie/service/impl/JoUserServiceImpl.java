package com.jomkie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jomkie.common.BaseServiceImpl;
import com.jomkie.dao.JoUserMapper;
import com.jomkie.dto.JoUserDto;
import com.jomkie.datastructure.model.JoUser;
import com.jomkie.service.JoUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Jomkie
 * @since 2021/3/28 21:08:03
 * 用户服务实现
 */
@Transactional
@Service
public class JoUserServiceImpl extends BaseServiceImpl<JoUserMapper, JoUser> implements JoUserService {

    @Autowired
    private JoUserMapper joUserMapper;

    @Override
    public List<JoUserDto> getAll() {
        QueryWrapper<JoUser> queryWrapper = new QueryWrapper<>();

        List<JoUser> list = joUserMapper.selectList(queryWrapper);
        return list.stream().map(bean -> {
            JoUserDto dto = new JoUserDto();
            BeanUtils.copyProperties(bean, dto);
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public JoUserDto getById(Long id) {
        JoUser joUser = joUserMapper.selectById(id);
        return Optional.ofNullable(joUser).map(bean -> {
            JoUserDto dto = new JoUserDto();
            BeanUtils.copyProperties(bean, dto);
            return dto;
        }).orElse(null);
    }

    @Override
    public boolean saveEntity(JoUser joUser) {
        return joUserMapper.insert(joUser) == 1;
    }


    @Override
    public List<JoUser> findByConditions() {
        QueryWrapper<JoUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .like(true, JoUser::getEmail, "@baomidou.com")
                .ge(JoUser::getAge, 9);

        joUserMapper.selectMaps(queryWrapper);
        return null;
    }

	@Override
	public void updateBatchEnity(List<JoUser> list) {
		updateBatchById(list);
	}

	@Override
	public boolean saveBatchEntity(List<JoUser> list, int batch) {
		saveBatch(list);
		return true;
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public List<JoUser> handlePage(int currentPage, int pageSize) {
		IPage<JoUser> page = new Page<>(currentPage, pageSize);
		page(page, Wrappers.<JoUser>lambdaQuery().orderByAsc(JoUser::getAge));
		List<JoUser> record = page.getRecords();
		record.forEach(e -> e.setAge(22));
		updateBatchById(record);
		return record;
	}
	
	public void updateEntity(JoUserDto dto) {
		JoUser joUser = super.getById(dto.getId());
		BeanUtils.copyProperties(dto, joUser);
		updateById(joUser);
	}


}
