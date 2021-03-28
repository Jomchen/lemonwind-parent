package com.jomkie.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@TableName("jo_user")
public class JoUser {

    private Long id;
    private String name;
    private Integer age;
    private String email;

}
