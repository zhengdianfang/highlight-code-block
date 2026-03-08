package com.zhengdianfang.highlightr.themes

import android.graphics.Color

object AtomDarkTheme : Theme {
    private val styles = mapOf(
        "keyword" to Style(Color.parseColor("#c678dd")),
        "built_in" to Style(Color.parseColor("#e6c07b")),
        "literal" to Style(Color.parseColor("#56b6c2")),
        "string" to Style(Color.parseColor("#98c379")),
        "comment" to Style(Color.parseColor("#5c6370"), italic = true),
        "variable" to Style(Color.parseColor("#d19a66")),
        "doctag" to Style(Color.parseColor("#c678dd")),
        "number" to Style(Color.parseColor("#d19a66")),
        "constant" to Style(Color.parseColor("#d19a66")),
        "type" to Style(Color.parseColor("#e6c07b")),
        "title" to Style(Color.parseColor("#e6c07b")),
        "field" to Style(Color.parseColor("#e6c07b")),
        "function" to Style(Color.parseColor("#61aeee")),
        "meta" to Style(Color.parseColor("#61aeee")),
        "keyword_void" to Style(Color.parseColor("#c678dd")),
        "template-variable" to Style(Color.parseColor("#e06c75")),
        "function-call" to Style(Color.parseColor("#e6c07b"))
    )

    override fun styleFor(scope: String): Style? {
        if (scope.startsWith("selector-")) {
            return when (scope) {
                "selector-tag" -> styles["template-variable"]
                "selector-id" -> styles["literal"]
                "selector-class" -> styles["variable"]
                else -> null
            }
        }
        return styles[scope]
    }

    override val background: Int = Color.parseColor("#282c34")
    override val foreground: Int = Color.parseColor("#abb2bf")
}
