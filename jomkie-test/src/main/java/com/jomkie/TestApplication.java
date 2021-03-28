package com.jomkie;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author Jomkie
 * @date 2021/3/27 22:35:52
 * 测试服务
 */
@SpringBootApplication
@MapperScan("com.jomkie.**.dao")
@EnableAspectJAutoProxy
public class TestApplication {

    /**
     * @author Jomkie
     * @date 2021/3/27 22:36:10
     * 执行主程序
     */
    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }

}
