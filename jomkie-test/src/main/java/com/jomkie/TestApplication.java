package com.jomkie;

/**
 * @author Jomkie
 * @date 2021/3/27 22:35:52
 * 测试
 */
@SpringBootApplication
@MapperScan("com.jomkie.**.dao")
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
