package com.jomkie.common.task;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jomkie.datastructure.model.JoUser;
import com.jomkie.service.impl.JoUserServiceImpl;

/**
 * 定时器
 * @author jomkie
 */
@EnableScheduling
@Configuration
public class TimeTask {

	@Autowired
	private JoUserServiceImpl joUserServiceImpl;
	
	int allCount = 0;
	int currentPage = 1;
	int currentCount = 0;
	
	/**
	 * 五秒执行一次
	 */
	@Scheduled(cron = "*/5 * * * * ?")
	private void testTimeTask() {
		if (allCount == 0) {
			allCount = joUserServiceImpl.count();
			currentPage = 1;
		}
		
		if (currentCount >= allCount) {
			currentCount = allCount = 0;
			currentPage = 1;
		}
		
		System.out.println(currentPage +"--------" + 5);
		IPage<JoUser> page = new Page<>(currentPage, 5);
		joUserServiceImpl.page(page, Wrappers.<JoUser>lambdaQuery().orderByAsc(JoUser::getAge));
		List<JoUser> record = page.getRecords();
		
		currentPage += 1;
		currentCount += record.size();
		
		String printData = record.stream().map(JoUser::getAge).map(String::valueOf).collect(Collectors.joining(","));
		System.out.println(record.size() + "条数据：" + printData);
		
	}
	
}
