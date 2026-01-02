package com.zhengdianfang.highlightr

import android.app.Activity
import android.os.Bundle
import android.widget.LinearLayout
import com.zhengdianfang.highlightr.ui.HighlightTextView

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL
        layout.setPadding(32, 32, 32, 32)
        layout.setBackgroundColor(0xFF000000.toInt()) // Black background
        
        val highlightTextView = HighlightTextView(this)
        highlightTextView.textSize = 16f
        highlightTextView.setSource(
            """
            // Example Kotlin code
            package com.example
            
            fun main(args: Array<String>) {
                val greeting = "Hello, World!"
                println(greeting)
            }
            """.trimIndent(),
            "kotlin"
        )
        
        layout.addView(highlightTextView)
        setContentView(layout)
    }
}
