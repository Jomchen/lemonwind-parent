package com.lemonwind.test.typetest.entity;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class JoUser {

  private Long id;
  private String name;
  private Integer age;

  public JoUser() {
  }

  public JoUser(Long id, String name, Integer age) {
    this.id = id;
    this.name = name;
    this.age = age;
  }

}
