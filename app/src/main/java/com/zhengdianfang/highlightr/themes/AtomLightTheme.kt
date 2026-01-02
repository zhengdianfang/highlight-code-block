package com.zhengdianfang.highlightr.themes

import android.graphics.Color

object AtomLightTheme : Theme {
    private val styles = mapOf(
        "keyword" to Style(Color.parseColor("#a626a4")),
        "built_in" to Style(Color.parseColor("#c18401")),
        "literal" to Style(Color.parseColor("#0184bb")),
        "string" to Style(Color.parseColor("#50a14f")),
        "comment" to Style(Color.parseColor("#a0a1a7"), italic = true),
        "variable" to Style(Color.parseColor("#986801")),
        "doctag" to Style(Color.parseColor("#a626a4"))
    )

    override fun styleFor(scope: String): Style? {
        return styles[scope]
    }

    override val background: Int = Color.parseColor("#fafafa")
    override val foreground: Int = Color.parseColor("#383a42")
}
