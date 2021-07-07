package com.datastructure.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data(staticConstructor = "of")
@AllArgsConstructor
@NoArgsConstructor
public class DataUser {

    private Long id;

    private String name;

    private Integer age;

    private String address;

    private Date birthday;

}
