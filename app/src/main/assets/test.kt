package com.example.test

class TestKotlin(val name: String) {
    fun sayHello() {
        println("Hello, $name")
    }
}

fun main() {
    val test = TestKotlin("World")
    test.sayHello()

    val list = listOf("Java", "Kotlin")
    list.forEach { 
        println(it)
    }
    
    val map = mapOf("key" to "value")
    println(map["key"])
}
