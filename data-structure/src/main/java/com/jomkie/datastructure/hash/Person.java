package com.jomkie.datastructure.hash;


public class Person {

    private int age;
    private float height;
    private String name;

    public Person(int age, float height, String name) {
        this.age = age;
        this.height = height;
        this.name = name;
    }

    @Override
    public int hashCode() {
        int hashCode = Integer.hashCode(age);
        hashCode = hashCode * 31 + Float.hashCode(height);
        hashCode = hashCode * 31 + (name != null ? name.hashCode() : 0);
        return hashCode;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) { return true; }
        if (obj == null || obj.getClass() != getClass()) { return false; }
        // 不建议使用下面一行代码，因为如果 obj 是 Person 的子类也会认为可能是相同的对象
        //if (obj == null || !(obj instanceof Person)) { return false; }

        Person person = (Person) obj;
        return person.age == age
                && person.height == height
                && person.name == null ? name == null : person.name.equals(name);
    }
}
