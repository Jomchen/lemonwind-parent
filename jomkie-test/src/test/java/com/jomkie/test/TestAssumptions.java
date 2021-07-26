package com.jomkie.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assumptions.*;

@Slf4j
@Disabled("Disabled until bug #42 has been resolved")
public class TestAssumptions {

    @Test
    public void test00() {
        assumeTrue(new Random().nextBoolean());
    }

    @Test
    public void test01() {
        assumeTrue(
                "1".equals(new Integer(2).toString()),
                () -> "the test has failed"
        );
    }

    @Test
    public void test02() {
        // 如果 assumption 为 false 则不执行 assumingThat 内的其它验证
        assumingThat(
                true,
                () -> {
                    Assertions.assertEquals(2, Math.floorDiv(4, 1));
                }
        );

        Assertions.assertEquals(42, Math.multiplyExact(6, 7));
    }

}
