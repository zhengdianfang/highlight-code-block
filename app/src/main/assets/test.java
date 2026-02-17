package com.example.test;

import java.util.ArrayList;
import java.util.List;

public class TestJava {
    private String name;

    public TestJava(String name) {
        this.name = name;
    }

    public void sayHello() {
        System.out.println("Hello, " + name);
    }

    public static void main(String[] args) {
        TestJava test = new TestJava("World");
        test.sayHello();
        
        List<String> list = new ArrayList<>();
        list.add("Java");
        list.add("Kotlin");
        
        for (String s : list) {
            System.out.println(s);
        }
    }
}
