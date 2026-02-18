package com.zhengdianfang.highlightr.languages

import com.zhengdianfang.highlightr.Mode

object XmlLanguage {
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

        // New mode for XML Processing Instruction
        val XML_META = Mode(
             className = "meta",
             begin = "<\\?",
             end = "\\?>",
             contains = listOf(
                 Mode(className = "literal", begin = "[\\w-]+", end = ""), // Match words as literal
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
                XML_META,
                COMMENT,
                DOCTYPE,
                TAG,
                SYMBOL
            )
        )
    }
}
