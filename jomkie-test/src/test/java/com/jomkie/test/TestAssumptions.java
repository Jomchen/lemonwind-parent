package com.jomkie.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assumptions.*;

@Slf4j
public class TestAssumptions {

    @Test
    public void test00() {
        assumeTrue(new Random().nextBoolean());
    }

    @Test
    public void test01() {
        assumeTrue(
                "1".equals("2"),
                () -> "ksksk"
        );
    }

}
