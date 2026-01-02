package com.zhengdianfang.highlightr.themes

data class Style(
    val color: Int,
    val bold: Boolean = false,
    val italic: Boolean = false
)

interface Theme {
    val background: Int
    val foreground: Int
    fun styleFor(scope: String): Style?
    
    // Deprecated or convenience, mapped to styleFor
    fun colorFor(scope: String): Int? = styleFor(scope)?.color
}
