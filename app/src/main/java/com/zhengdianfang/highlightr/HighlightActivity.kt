package com.zhengdianfang.highlightr

import android.app.Activity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.ScrollView
import com.zhengdianfang.highlightr.ui.HighlightTextView
import java.io.BufferedReader
import java.io.InputStreamReader

class HighlightActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val language = intent.getStringExtra("language") ?: "kotlin"
        val fileName = intent.getStringExtra("fileName") ?: "test.kt"

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
        
        val highlightTextView = HighlightTextView(this)
        highlightTextView.textSize = 16f
        
        val sourceCode = readAssetFile(fileName)
        highlightTextView.setSource(sourceCode, language)
        
        layout.addView(highlightTextView)
        scrollView.addView(layout)
        setContentView(scrollView)
    }

    private fun readAssetFile(fileName: String): String {
        return try {
            assets.open(fileName).use { inputStream ->
                InputStreamReader(inputStream).use { reader ->
                    BufferedReader(reader).use { bufferedReader ->
                        bufferedReader.readText()
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            "Error reading file: $fileName"
        }
    }
}
