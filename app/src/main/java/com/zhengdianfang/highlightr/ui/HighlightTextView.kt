package com.zhengdianfang.highlightr.ui

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.util.AttributeSet
import android.widget.TextView
import com.zhengdianfang.highlightr.HighlightEngine
import com.zhengdianfang.highlightr.TokenTreeEmitter
import com.zhengdianfang.highlightr.languages.JavaLanguage
import com.zhengdianfang.highlightr.languages.JavascriptLanguage
import com.zhengdianfang.highlightr.languages.KotlinLanguage
import com.zhengdianfang.highlightr.languages.CppLanguage
import com.zhengdianfang.highlightr.languages.CssLanguage
import com.zhengdianfang.highlightr.languages.HtmlLanguage
import com.zhengdianfang.highlightr.languages.XmlLanguage
import com.zhengdianfang.highlightr.languages.SwiftLanguage
import com.zhengdianfang.highlightr.languages.PythonLanguage
import com.zhengdianfang.highlightr.themes.AtomLightTheme
import com.zhengdianfang.highlightr.themes.Theme
import java.util.ArrayDeque

class HighlightTextView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : TextView(context, attrs) {

    private val engine = HighlightEngine()
    
    var theme: Theme = AtomLightTheme
        set(value) {
            field = value
            applyTheme()
            if (sourceCode.isNotEmpty()) {
                setSource(sourceCode, languageName)
            }
        }
        
    private var sourceCode: String = ""
    private var languageName: String = "kotlin"

    init {
        // Register default languages
        engine.registerLanguage("kotlin", KotlinLanguage.get())
        engine.registerLanguage("java", JavaLanguage.get())
        engine.registerLanguage("javascript", JavascriptLanguage.get())
        engine.registerLanguage("cpp", CppLanguage.get())
        engine.registerLanguage("css", CssLanguage.get())
        engine.registerLanguage("html", HtmlLanguage.get())
        engine.registerLanguage("xml", XmlLanguage.get())
        engine.registerLanguage("swift", SwiftLanguage.get())
        engine.registerLanguage("python", PythonLanguage.get())
        
        applyTheme()
    }
    
    private fun applyTheme() {
        setBackgroundColor(theme.background)
        setTextColor(theme.foreground)
    }

    fun setSource(code: String, language: String) {
        this.sourceCode = code
        this.languageName = language
        try {
            val result = engine.highlight(code, language)
            text = render(result)
        } catch (e: Exception) {
            // Fallback to plain text if error occurs
            text = code
            e.printStackTrace()
        }
    }

    private fun render(result: HighlightEngine.Result): Spannable {
        val builder = SpannableStringBuilder()
        
        val startOffsets = ArrayDeque<Int>()
        val scopeNames = ArrayDeque<String>()
        
        data class SpanInfo(val start: Int, val end: Int, val scope: String, val depth: Int)
        val spans = mutableListOf<SpanInfo>()
        var currentDepth = 0

        result.events.forEach { event ->
            when (event) {
                is TokenTreeEmitter.Event.Text -> {
                    builder.append(event.text)
                }
                is TokenTreeEmitter.Event.Start -> {
                    startOffsets.push(builder.length)
                    scopeNames.push(event.scope)
                    currentDepth++
                }
                is TokenTreeEmitter.Event.End -> {
                    if (!startOffsets.isEmpty()) {
                        val start = startOffsets.pop()
                        val scope = scopeNames.pop()
                        val end = builder.length
                        currentDepth--
                        
                        spans.add(SpanInfo(start, end, scope, currentDepth))
                    }
                }
            }
        }
        
        // Sort spans by depth (ascending), so deeper spans are applied last and override shallower ones
        spans.sortBy { it.depth }
        
        spans.forEach { span ->
            val style = theme.styleFor(span.scope)
            if (style != null) {
                builder.setSpan(
                    ForegroundColorSpan(style.color),
                    span.start,
                    span.end,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                
                if (style.bold) {
                    builder.setSpan(
                        StyleSpan(Typeface.BOLD),
                        span.start,
                        span.end,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                }
                
                if (style.italic) {
                    builder.setSpan(
                        StyleSpan(Typeface.ITALIC),
                        span.start,
                        span.end,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                }
            }
        }
        
        return builder
    }
}
