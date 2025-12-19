package com.zhengdianfang.highlightr

import android.graphics.Typeface
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import com.zhengdianfang.highlightr.languages.JavaLanguage
import com.zhengdianfang.highlightr.languages.JavaScriptLanguage
import com.zhengdianfang.highlightr.languages.KotlinLanguage
import com.zhengdianfang.highlightr.theme.DefaultThemes

class MainActivity : ComponentActivity() {
    private lateinit var tvCode: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvCode = findViewById(R.id.tvCode)

        JavaScriptLanguage.register()
        KotlinLanguage.register()
        JavaLanguage.register()

        val sampleCode = """
          public class Singleton {

            private static Singleton instance = new Singleton();

            private Singleton() {}

            public static Singleton getInstance() {
                return instance;
            }

        } 
        """.trimIndent()

        CodeHighlighter.highlightInto(
            textView = tvCode,
            code = sampleCode,
            languageId =  JavaLanguage.LANGUAGE_NAME,
            theme = DefaultThemes.LightTheme,
            scope = lifecycleScope
        )

        tvCode.apply {
            setTextIsSelectable(true)
            typeface = Typeface.MONOSPACE
            setHorizontallyScrolling(true)
            movementMethod = ScrollingMovementMethod.getInstance()
        }
    }
}