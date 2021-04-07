package com.jomkie;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
  * @author Jomkie
  * @date 2021-04-2 23:59
  * 测试服务
  */
@SpringBootApplication
@MapperScan("com.jomkie.**.dao")
@EnableAspectJAutoProxy
public class TestApplication {

    /**
     * @author Jomkie
     * @date 2021-04-3 0:0
     * 执行主程序
     */
    public static void main(String[] args) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        /*SpringApplication.run(TestApplication.class, args);*/
    }

}
