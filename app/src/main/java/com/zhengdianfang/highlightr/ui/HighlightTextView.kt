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
import com.zhengdianfang.highlightr.languages.KotlinLanguage
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

        result.events.forEach { event ->
                println(event)
            when (event) {
                is TokenTreeEmitter.Event.Text -> {
                    builder.append(event.text)
                }
                is TokenTreeEmitter.Event.Start -> {
                    startOffsets.push(builder.length)
                    scopeNames.push(event.scope)
                }
                is TokenTreeEmitter.Event.End -> {
                    if (!startOffsets.isEmpty()) {
                        val start = startOffsets.pop()
                        val scope = scopeNames.pop()
                        val end = builder.length
                        
                        val style = theme.styleFor(scope)
                        if (style != null) {
                            builder.setSpan(
                                ForegroundColorSpan(style.color),
                                start,
                                end,
                                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                            )
                            
                            if (style.bold) {
                                builder.setSpan(
                                    StyleSpan(Typeface.BOLD),
                                    start,
                                    end,
                                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                                )
                            }
                            
                            if (style.italic) {
                                builder.setSpan(
                                    StyleSpan(Typeface.ITALIC),
                                    start,
                                    end,
                                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                                )
                            }
                        }
                    }
                }
            }
        }
        
        return builder
    }
}
