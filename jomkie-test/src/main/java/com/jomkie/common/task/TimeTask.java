package com.jomkie.common.task;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

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
	
	AtomicBoolean atomicBoolean = new AtomicBoolean(true);
	
	int allCount = 0;
	int currentPage = 1;
	int currentCount = 0;
	
	/**
	 * 五秒执行一次
	 */
	@Scheduled(cron = "*/5 * * * * ?")
	private void testTimeTask() {
		boolean execute = atomicBoolean.compareAndSet(true, false);
		if (!execute) {
			return;
		}
		
		if (allCount == 0) {
			//allCount = joUserServiceImpl.count();
			currentPage = 1;
		}
		
		if (currentCount >= allCount) {
			currentCount = allCount = 0;
			currentPage = 1;
		}
		
		System.out.println(currentPage +"--------" + 5);
		// 持久化不生效
		List<JoUser> record = joUserServiceImpl.handlePage(currentPage, 5);
		
		currentPage += 1;
		currentCount += record.size();
		
		atomicBoolean.set(true);
	}
	
}
