package com.zhengdianfang.highlightr.themes

import android.graphics.Color

object MonokaiTheme : Theme {
    private val styles = mapOf(
        "keyword" to Style(Color.parseColor("#F92672"), bold = true),
        "built_in" to Style(Color.parseColor("#66D9EF"), italic = true),
        "literal" to Style(Color.parseColor("#AE81FF")),
        "string" to Style(Color.parseColor("#E6DB74")),
        "comment" to Style(Color.parseColor("#75715E")),
        "variable" to Style(Color.parseColor("#FD971F")),
        "doctag" to Style(Color.parseColor("#75715E"))
    )

    override fun styleFor(scope: String): Style? {
        return styles[scope]
    }

    override val background: Int = Color.parseColor("#272822")
    override val foreground: Int = Color.parseColor("#F8F8F2")
}
