package com.zhengdianfang.highlightr

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.Spinner
import com.zhengdianfang.highlightr.themes.AtomDarkTheme
import com.zhengdianfang.highlightr.themes.AtomLightTheme
import com.zhengdianfang.highlightr.themes.GitHubTheme
import com.zhengdianfang.highlightr.themes.MonokaiTheme
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

        val themes = mapOf(
            "Atom Dark" to AtomDarkTheme,
            "Atom Light" to AtomLightTheme,
            "GitHub" to GitHubTheme,
            "Monokai" to MonokaiTheme
        )

        val themeSpinner = Spinner(this)
        val adapter = object : ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, themes.keys.toList()) {
            override fun getView(position: Int, convertView: View?, parent: android.view.ViewGroup): View {
                val view = super.getView(position, convertView, parent)
                if (view is android.widget.TextView) {
                    val themeName = getItem(position)
                    val theme = themes[themeName]
                    if (theme != null) {
                        view.setTextColor(theme.foreground)
                    }
                }
                return view
            }

            override fun getDropDownView(position: Int, convertView: View?, parent: android.view.ViewGroup): View {
                val view = super.getDropDownView(position, convertView, parent)
                if (view is android.widget.TextView) {
                    view.setTextColor(0xFF000000.toInt()) // Black text
                    view.setBackgroundColor(0xFFFFFFFF.toInt()) // White background
                }
                return view
            }
        }
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        themeSpinner.adapter = adapter
        themeSpinner.background = null

        themeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val themeName = parent.getItemAtPosition(position) as String
                val theme = themes[themeName]
                if (theme != null) {
                    highlightTextView.theme = theme
                    // Update background color of the layout to match the theme background
                    layout.setBackgroundColor(theme.background)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Do nothing
            }
        }
        
        layout.addView(themeSpinner)
        
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
