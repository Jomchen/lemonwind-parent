package com.jomkie.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class JoUser {

    private Long id;
    private String name;
    private Integer age;
    private String email;

}
