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
                Mode(className = "template-variable", begin = "\\$[A-Za-z0-9_]+", end = ""),
                Mode(className = "template-variable", begin = "\\$\\{", end = "\\}", contains = listOf(
                    Mode(className = "string", begin = "\"", end = "\""),
                    Mode(className = "number", begin = "\\b\\d+(\\.\\d+)?f?\\b", end = "")
                ))
            )
        )

        val NUMBER = Mode(
            className = "number",
            begin = "\\b0x[0-9a-fA-F]+\\b|\\b\\d+(\\.\\d+)?([eE][-+]?\\d+)?f?\\b",
            end = ""
        )
        
        val ANNOTATION = Mode(
            className = "meta",
            begin = "@[A-Za-z0-9_]+",
            end = ""
        )
        
        val FUNCTION = Mode(
            className = "function",
            begin = "(?<=\\bfun\\s)[A-Za-z0-9_]+",
            end = ""
        )
        
        val CLASS = Mode(
            className = "title",
            begin = "(?<=\\bclass\\s)[A-Za-z0-9_]+",
            end = ""
        )
        
        val INTERFACE = Mode(
            className = "title",
            begin = "(?<=\\binterface\\s)[A-Za-z0-9_]+",
            end = ""
        )
        
        val OBJECT = Mode(
            className = "title",
            begin = "(?<=\\bobject\\s)[A-Za-z0-9_]+",
            end = ""
        )
        
        val BUILT_INS = KEYWORDS["built_in"]?.replace(" ", "|") ?: ""
        val TYPE = Mode(
            className = "type",
            begin = "\\b(?!($BUILT_INS)\\b)[A-Z][A-Za-z0-9_]*\\b",
            end = ""
        )

        return Mode(
            className = "kotlin",
            keywords = KEYWORDS,
            contains = listOf(
                Mode(className = "comment", begin = "//", end = "\n"),
                Mode(className = "comment", begin = "/\\*", end = "\\*/"),
                STRING,
                NUMBER,
                ANNOTATION,
                FUNCTION,
                CLASS,
                INTERFACE,
                OBJECT,
                TYPE
            )
        )
    }
}
