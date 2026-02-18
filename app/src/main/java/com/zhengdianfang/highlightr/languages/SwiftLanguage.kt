package com.zhengdianfang.highlightr.languages

import com.zhengdianfang.highlightr.Mode

object SwiftLanguage {
    fun get(): Mode {
        val KEYWORDS = mapOf(
            "keyword" to "class deinit enum extension func import init inout internal let operator private protocol public static struct subscript typealias var break case continue default do else fallthrough for if in return switch where while as dynamicType false is nil self Self super true __COLUMN__ __FILE__ __FUNCTION__ __LINE__ try catch throw throws rethrows defer guard repeat open fileprivate",
            "built_in" to "Int Double Float Bool String Array Dictionary Set Character Any AnyObject Optional",
            "literal" to "true false nil"
        )

        val STRING = Mode(
            className = "string",
            begin = "\"",
            end = "\"",
            contains = listOf(
                Mode(className = "template-variable", begin = "\\\\\\(", end = "\\)")
            )
        )

        val NUMBER = Mode(
            className = "number",
            begin = "\\b\\d+(\\.\\d+)?",
            end = ""
        )

        val COMMENT_SINGLE = Mode(
            className = "comment",
            begin = "//",
            end = "\n"
        )

        val COMMENT_BLOCK = Mode(
            className = "comment",
            begin = "/\\*",
            end = "\\*/"
        )

        val ATTRIBUTE = Mode(
            className = "keyword",
            begin = "@[A-Za-z_][A-Za-z0-9_]*",
            end = ""
        )
        
        val FUNCTION = Mode(
            className = "function",
            begin = "(?<=\\bfunc\\s)[A-Za-z0-9_]+",
            end = ""
        )

        val TYPE = Mode(
            className = "type",
            begin = "\\b[A-Z][A-Za-z0-9_]*\\b",
            end = ""
        )

        return Mode(
            className = "swift",
            keywords = KEYWORDS,
            contains = listOf(
                STRING,
                NUMBER,
                COMMENT_SINGLE,
                COMMENT_BLOCK,
                ATTRIBUTE,
                FUNCTION,
                TYPE
            )
        )
    }
}
