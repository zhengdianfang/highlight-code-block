package com.zhengdianfang.highlightr.themes

import android.graphics.Color

object GitHubTheme : Theme {
    private val styles = mapOf(
        "keyword" to Style(Color.parseColor("#D73A49"), bold = true),
        "built_in" to Style(Color.parseColor("#005CC5")),
        "literal" to Style(Color.parseColor("#005CC5")),
        "string" to Style(Color.parseColor("#032F62")),
        "comment" to Style(Color.parseColor("#6A737D"), italic = true),
        "variable" to Style(Color.parseColor("#24292E")),
        "doctag" to Style(Color.parseColor("#6A737D"))
    )

    override fun styleFor(scope: String): Style? {
        return styles[scope]
    }

    override val background: Int = Color.parseColor("#FFFFFF")
    override val foreground: Int = Color.parseColor("#24292E")
}
