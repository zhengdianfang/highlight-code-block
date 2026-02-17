package com.zhengdianfang.highlightr

import android.app.Activity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.ScrollView
import com.zhengdianfang.highlightr.ui.HighlightTextView

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val scrollView = ScrollView(this)
        scrollView.layoutParams = android.view.ViewGroup.LayoutParams(
            android.view.ViewGroup.LayoutParams.MATCH_PARENT,
            android.view.ViewGroup.LayoutParams.MATCH_PARENT
        )
        scrollView.isFillViewport = true
        
        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL
        layout.setPadding(32, 32, 32, 32)
        layout.setBackgroundColor(0xFF000000.toInt()) // Black background
        
        val kotlinHighlightTextView = HighlightTextView(this)
        kotlinHighlightTextView.textSize = 16f
        kotlinHighlightTextView.setPadding(0, 50, 0, 0)
        kotlinHighlightTextView.setSource(
            """
            package com.example.kotlin

            import java.util.Date

            @Target(AnnotationTarget.CLASS)
            annotation class MyAnnotation

            @MyAnnotation
            open class BaseClass(val id: Int) {
                open fun printId() {
                    println("ID: ${'$'}id")
                }
            }

            object Singleton {
                const val PI = 3.14159
            }

            interface Printable {
                fun print()
            }

            /**
             * A comprehensive Kotlin example
             */
            data class User(val name: String, var age: Int) : BaseClass(1), Printable {
                
                val isActive: Boolean = true
                val score: Double = 98.5
                val hexValue: Int = 0xFF00AA
                val floatValue: Float = 1.5f
                
                override fun print() {
                    // String interpolation
                    println("User: ${'$'}name, Age: ${'$'}age")
                    
                    val message = "Score: ${'$'}score"
                    if (isActive) {
                        println(message)
                    }
                    
                    val list = listOf(1, 2, 3)
                    for (item in list) {
                        println("Item: ${'$'}item")
                    }
                    
                    when (age) {
                        in 0..18 -> println("Minor")
                        else -> println("Adult")
                    }
                }
                
                companion object {
                    @JvmStatic
                    fun main(args: Array<String>) {
                        val user = User("Alice", 30)
                        user.print()
                        println("PI: ${'$'}{Singleton.PI}")
                    }
                }
            }
            """.trimIndent(),
            "kotlin"
        )
        layout.addView(kotlinHighlightTextView)

        scrollView.addView(layout)
        setContentView(scrollView)
    }
}
