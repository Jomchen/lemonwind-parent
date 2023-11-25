package com.lemonwind.run;

import com.lemonwind.test.common.LemonException;
import com.lemonwind.test.common.Responsecode;
import com.lemonwind.test.model.JoUser;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;


import java.time.Duration;
import java.util.Random;

@Slf4j
/*@DisplayName("Jomkie")*/
/*@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)*/
@IndicativeSentencesGeneration(separator = "#", generator = DisplayNameGenerator.ReplaceUnderscores.class)
public class TestAssertionsApplication {

    @BeforeAll
    static void beforeAll() {
        log.info("beforeAll");
    }

    @AfterAll
    static void afterAll() {
        log.info("afterAll");
    }

    @BeforeEach
    void beforEach() {
        log.info("beforEach");
    }

    @AfterEach
    void afterEatch() {
        log.info("afterEatch");
    }

    @Test
    @Disabled("for demonstration purposes")
    void skippedTest() {
        log.info("skippedTest");
    }

    /** 自定义显示名字 */
    @Test
    @DisplayName("the test00 for Jomkie")
    void test00() {
        log.info("test00");
    }

    /** 自定义名字（取消下横线） */
    @Test
    void test_01() {
        log.info("test01");
    }

    /** 验证是否相等 */
    @Test
    void test02() {
        int data = new Random().nextInt(10);
        assertEquals(5, data);
    }

    private final JoUser joUser = new JoUser(1L, "Jomkie", 20, (short)1, "xxxx@qq.com");
    /** 验证一个组，这个组的所有断言都会验证 */
    @Test
    void test03() {
        assertAll(
                "userGroup",
                () -> assertEquals("Jomkie", joUser.getName()),
                () -> assertEquals(22, joUser.getAge())
        );
    }

    /** 断言依赖 */
    @Test
    void test04() {
        assertAll(
                "dependentAssertions",
                () -> {
                    assertNull(joUser);
                    // 如果上面的断言不通过，则这之下的断言不验证
                    assertEquals(22, joUser.getAge());
                }
        );
    }

    @Test
    void test05() {
        assertAll(
                "testGroup",
                () -> {
                    String firstName = "0";
                    assertAll(() -> {
                        assertTrue(firstName.equals("0"));
                    });
                },
                () -> {
                    String lastName = "0";
                    assertAll(() -> {
                        assertTrue(lastName.equals("0"));
                    });
                }
        );
    }

    /** 断言会抛出的异常 */
    @Test
    void test06() {
        LemonException lemonException = assertThrows(LemonException.class, () -> {
            if (3 > 2) {
                throw new LemonException(Responsecode.FAILE, "柠檬异常");
            }
        });
        assertEquals(-1, lemonException.getCode());
    }

    /** 测试超时 */
    @Test
    void test07() {
        assertTimeout(Duration.ofSeconds(3), () -> {
            log.info("executing a long time task...");
            Thread.sleep(4000);
        });
    }

    /** 测试超时抢先终止 */
    @Test
    void test08() {
        assertTimeoutPreemptively(Duration.ofSeconds(3), () -> {
            log.info("executing a long time task2...");
            Thread.sleep(5000);
        });
    }

}
