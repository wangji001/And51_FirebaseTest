package edu.android.and51_firebasetest;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Person {
    private String id;
    private String name;
    private String age;

    public Person(){}

    public Person(String id, String name, String age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", age='" + age + '\'' +
                '}';
    }

    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String , Object> result = new HashMap<>();

        result.put("id", id);
        result.put("name", name);
        result.put("age", age);
        return result;
    }
} // end class Person
