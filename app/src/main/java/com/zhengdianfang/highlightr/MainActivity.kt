package com.zhengdianfang.highlightr

import android.graphics.Typeface
import android.os.Bundle
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.zhengdianfang.highlightr.languages.KotlinLanguage
import com.zhengdianfang.highlightr.theme.DefaultThemes
import com.zhengdianfang.highlightr.ui.theme.HighlightrTheme

class MainActivity : ComponentActivity() {
    private lateinit var tvCode: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvCode = findViewById(R.id.tvCode)

        // 注册 Kotlin 语言定义（正式项目你可以放在 Application 里）
        KotlinLanguage.register()

        val sampleCode = """
            package com.example.demo

            import kotlinx.coroutines.CoroutineScope
            import kotlinx.coroutines.Dispatchers
            import kotlinx.coroutines.launch

            // 简单的示例函数
            class HelloKotlin {

                fun sayHello(name: String?): String {
                    val safeName = name ?: "World"
                    println("Hello, safeName")

                    return "Hello, safeName"
                }

                suspend fun loadData(scope: CoroutineScope) {
                    scope.launch(Dispatchers.IO) {
                        // TODO: 执行耗时操作
                        val result = 42
                        println("Result = result")
                    }
                }
            }
        """.trimIndent()

        CodeHighlighter.highlightInto(
            textView = tvCode,
            code = sampleCode,
            languageId = "kotlin",
            theme = DefaultThemes.LightTheme,
            scope = lifecycleScope
        )

        // 双保险
        tvCode.typeface = Typeface.MONOSPACE
    }
}