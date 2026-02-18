package com.zhengdianfang.highlightr.languages

import com.zhengdianfang.highlightr.Mode

object HtmlLanguage {
    fun get(): Mode {
        val STRING = Mode(
            className = "string",
            begin = "\"",
            end = "\""
        )
        val APOS_STRING = Mode(
            className = "string",
            begin = "'",
            end = "'"
        )

        val TAG_NAME = Mode(
            className = "template-variable",
            begin = "(?<=<|/)[a-zA-Z0-9\\._-]+",
            end = ""
        )
        
        val ATTRIBUTE = Mode(
            className = "variable",
            begin = "[a-zA-Z0-9\\._-]+",
            end = ""
        )

        val STYLE_BLOCK = Mode(
            className = "template-variable",
            begin = "(?i)<style\\b[^>]*>",
            end = "(?i)</style>",
            subLanguage = "css"
        )

        val SCRIPT_BLOCK = Mode(
            className = "template-variable",
            begin = "(?i)<script\\b[^>]*>",
            end = "(?i)</script>",
            subLanguage = "javascript"
        )

        val TAG = Mode(
            className = "template-variable",
            begin = "<",
            end = ">",
            contains = listOf(
                TAG_NAME,
                ATTRIBUTE,
                STRING,
                APOS_STRING
            )
        )
        
        val DOCTYPE = Mode(
             className = "meta",
             begin = "<!DOCTYPE",
             end = ">",
             contains = listOf(
                 Mode(className = "string", begin = "\"", end = "\"")
             )
        )
        
        val COMMENT = Mode(
            className = "comment",
            begin = "<!--",
            end = "-->"
        )
        
        val SYMBOL = Mode(
            className = "constant",
            begin = "&[a-zA-Z0-9]+;",
            end = ""
        )

        return Mode(
            className = "xml",
            contains = listOf(
                COMMENT,
                DOCTYPE,
                STYLE_BLOCK,
                SCRIPT_BLOCK,
                TAG,
                SYMBOL
            )
        )
    }
}
