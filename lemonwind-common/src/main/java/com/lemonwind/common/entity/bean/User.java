package com.lemonwind.common.entity.bean;

import java.util.Date;

import com.lemonwind.common.entity.LemonwindEntity;
import lombok.*;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
public class User extends LemonwindEntity<User> {
	
    private String name;
    private Integer age;
    private String address;
    private Date birthday;

    public User() {
    }

    public User(Long id, String name, Integer age, String address, Date birthday) {
        super(id);
        this.name = name;
        this.age = age;
        this.address = address;
        this.birthday = birthday;
    }

}
