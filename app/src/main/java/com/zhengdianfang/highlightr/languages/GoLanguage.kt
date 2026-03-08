package com.zhengdianfang.highlightr.languages

import com.zhengdianfang.highlightr.Mode

object GoLanguage {
    fun get(): Mode {
        val KEYWORDS = mapOf(
            "keyword" to "break case chan const continue default defer else fallthrough for func go goto if import interface map package range return select struct switch type var",
            "built_in" to "append cap close complex copy delete imag len make new panic print println real recover",
            "type" to "bool byte complex64 complex128 error float32 float64 int int8 int16 int32 int64 rune string uint uint8 uint16 uint32 uint64 uintptr",
            "literal" to "true false iota nil"
        )

        val STRING = Mode(
            className = "string",
            begin = "\"",
            end = "\"",
            contains = listOf(
                Mode(className = "variable", begin = "\\\\[btnfr\"'\\\\]", end = "")
            )
        )

        val RAW_STRING = Mode(
            className = "string",
            begin = "`",
            end = "`"
        )

        val NUMBER = Mode(
            className = "number",
            begin = "\\b\\d+(\\.\\d+)?(e[+-]?\\d+)?i?\\b",
            end = ""
        )
        
        val HEX_NUMBER = Mode(
            className = "number",
            begin = "\\b0x[0-9a-fA-F]+\\b",
            end = ""
        )

        val COMMENT_LINE = Mode(
            className = "comment",
            begin = "//",
            end = "\n"
        )

        val COMMENT_BLOCK = Mode(
            className = "comment",
            begin = "/\\*",
            end = "\\*/"
        )
        
        val FUNCTION = Mode(
            className = "function",
            begin = "\\bfunc\\s+\\w+",
            end = ""
        )

        return Mode(
            className = "go",
            keywords = KEYWORDS,
            contains = listOf(
                COMMENT_LINE,
                COMMENT_BLOCK,
                STRING,
                RAW_STRING,
                HEX_NUMBER,
                NUMBER,
                FUNCTION
            )
        )
    }
}
