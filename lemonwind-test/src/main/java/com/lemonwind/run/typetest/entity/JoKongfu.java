package com.lemonwind.run.typetest.entity;

public class JoKongfu {

  private String name;
  private Integer level;
  private JoUser joUser;

  public JoKongfu() {
  }

  public JoKongfu(String name, Integer level, JoUser joUser) {
    this.name = name;
    this.level = level;
    this.joUser = joUser;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getLevel() {
    return level;
  }

  public void setLevel(Integer level) {
    this.level = level;
  }

  public JoUser getJoUser() {
    return joUser;
  }

  public void setJoUser(JoUser joUser) {
    this.joUser = joUser;
  }
}
