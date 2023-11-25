package com.lemonwind.run.typetest.entity;

public class JoSon extends JoFather {

  private int id;
  private String name;
  private int age;

  public JoSon() {
    super();
  }

  public JoSon(int id, String name, int age) {
    this.id = id;
    this.name = name;
    this.age = age;
  }

  @Override
  public int getId() {
    return id;
  }

  @Override
  public void setId(int id) {
    this.id = id;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public void setName(String name) {
    this.name = name;
  }

  @Override
  public int getAge() {
    return age;
  }

  @Override
  public void setAge(int age) {
    this.age = age;
  }

}
