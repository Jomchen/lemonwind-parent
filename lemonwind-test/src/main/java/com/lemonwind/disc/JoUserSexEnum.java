package com.lemonwind.disc;

import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

/**
 * 性别枚举
 * @author Jomkie
 * @since 2021-06-24 10:14:11
 */
@Getter
public enum JoUserSexEnum {

    WOMAN(0, "女"),
    MAN(1, "男")
    ;

    Integer code;
    String msg;

    JoUserSexEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static JoUserSexEnum findByCode(Integer code) {
        return Arrays.stream(JoUserSexEnum.values())
                .filter(e -> Objects.equals(e.getCode(), code)).findFirst()
                .orElse(null);
    }

}
