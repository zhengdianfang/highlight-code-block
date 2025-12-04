package com.zhengdianfang.highlightr

import android.graphics.Typeface
import android.widget.TextView
import com.zhengdianfang.highlightr.model.CodeTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object CodeHighlighter {

    private val engine = HighlighterEngine()

    fun highlightInto(
        textView: TextView,
        code: String,
        languageId: String,
        theme: CodeTheme,
        scope: CoroutineScope
    ) {
        val language = LanguageRegistry.get(languageId) ?: run {
            textView.text = code
            textView.typeface = Typeface.MONOSPACE
            return
        }

        scope.launch(Dispatchers.Default) {
            val tokens = engine.highlight(code, language)
            val spannable = SpannableCodeRenderer.render(code, tokens, theme)

            withContext(Dispatchers.Main) {
                textView.text = spannable
                textView.typeface = Typeface.MONOSPACE
            }
        }
    }
}