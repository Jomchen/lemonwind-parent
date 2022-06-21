package com.jomkie.common.entity.bean;

import java.util.Date;

import lombok.*;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class JoUser {
	
    private Integer id;
    private String name;
    private Integer age;
    private String address;
    private Date birthday;

    public JoUser() {
    }

    public JoUser(Integer id, String name, Integer age, String address, Date birthday) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.address = address;
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        return "JoUser{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", address='" + address + '\'' +
                ", birthday=" + birthday +
                '}';
    }
    
}
