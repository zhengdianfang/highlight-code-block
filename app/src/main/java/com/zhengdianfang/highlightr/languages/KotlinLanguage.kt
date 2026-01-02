package com.zhengdianfang.highlightr.languages

import com.zhengdianfang.highlightr.Mode

object KotlinLanguage {
    fun get(): Mode {
        val KEYWORDS = mapOf(
            "keyword" to "val var fun class interface object package import return if else while for do try catch throw when is as in out public private protected internal abstract final open enum sealed annotation data companion override suspend yield expect actual const operator infix inline external tailrec crossinline noinline reified dynamic",
            "built_in" to "Boolean Byte Short Int Long Float Double Char String Unit Any Nothing Array List Map Set",
            "literal" to "true false null"
        )
        
        val STRING = Mode(
            className = "string",
            begin = "\"", 
            end = "\"",
            contains = listOf(
                Mode(className = "variable", begin = "\\$[A-Za-z0-9_]+")
            )
        )

        return Mode(
            className = "kotlin",
            keywords = KEYWORDS,
            contains = listOf(
                Mode(className = "comment", begin = "//", end = "\n"),
                Mode(className = "comment", begin = "/\\*", end = "\\*/"),
                STRING
            )
        )
    }
}
