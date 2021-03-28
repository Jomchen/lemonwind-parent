package com.jomkie.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class JoUserDto {

    private Long id;
    private String name;
    private Integer age;
    private String email;

}
