package com.zhengdianfang.highlightr

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL
        layout.gravity = android.view.Gravity.CENTER
        
        val javaButton = createButton("Java Test", "java", "test.java")
        val kotlinButton = createButton("Kotlin Test", "kotlin", "test.kt")
        val jsButton = createButton("JavaScript Test", "javascript", "test.js")
        val cppButton = createButton("C++ Test", "cpp", "test.cpp")
        val cssButton = createButton("CSS Test", "css", "test.css")
        val htmlButton = createButton("HTML Test", "html", "test.html")

        layout.addView(javaButton)
        layout.addView(kotlinButton)
        layout.addView(jsButton)
        layout.addView(cppButton)
        layout.addView(cssButton)
        layout.addView(htmlButton)

        setContentView(layout)
    }

    private fun createButton(text: String, language: String, fileName: String): Button {
        val button = Button(this)
        button.text = text
        button.setOnClickListener {
            val intent = Intent(this, HighlightActivity::class.java)
            intent.putExtra("language", language)
            intent.putExtra("fileName", fileName)
            startActivity(intent)
        }
        return button
    }
}
