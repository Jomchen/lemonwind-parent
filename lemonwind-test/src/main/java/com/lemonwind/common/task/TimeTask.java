package com.lemonwind.common.task;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.lemonwind.datastructure.model.JoUser;
import com.lemonwind.service.impl.JoUserServiceImpl;

/**
 * 定时器
 * 
 * @author jomkie
 */
@EnableScheduling
@Configuration
public class TimeTask {

	/**
	 * 五秒执行一次
	 */
	//@Scheduled(cron = "*/5 * * * * ?")
	private void testTimeTask() {

		//System.out.println(currentPage + "--------" + 5);
		JoUserServiceImpl joUserServiceImpl = ApplicationContextUtil.getContext().getBean(JoUserServiceImpl.class);

		// 持久化不生效
		List<JoUser> record = joUserServiceImpl.handlePage(1, 5);


	}

}
