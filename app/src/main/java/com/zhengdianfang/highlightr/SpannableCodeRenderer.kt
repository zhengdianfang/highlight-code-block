package com.zhengdianfang.highlightr

import com.zhengdianfang.highlightr.model.CodeTheme
import com.zhengdianfang.highlightr.model.CodeToken

object SpannableCodeRenderer {

    fun render(
        code: String,
        tokens: List<CodeToken>,
        theme: CodeTheme
    ): CharSequence {
        val ssb = android.text.SpannableStringBuilder(code)

        tokens.forEach { token ->
            val style = theme.styleFor(token.type)
            ssb.setSpan(
                android.text.style.ForegroundColorSpan(style.color),
                token.start,
                token.end,
                android.text.Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            if (style.bold || style.italic) {
                val styleFlag = when {
                    style.bold && style.italic -> android.graphics.Typeface.BOLD_ITALIC
                    style.bold -> android.graphics.Typeface.BOLD
                    style.italic -> android.graphics.Typeface.ITALIC
                    else -> android.graphics.Typeface.NORMAL
                }
                ssb.setSpan(
                    android.text.style.StyleSpan(styleFlag),
                    token.start,
                    token.end,
                    android.text.Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
        }

        return ssb
    }
}