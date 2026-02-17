package com.zhengdianfang.highlightr.languages

import com.zhengdianfang.highlightr.Mode

object JavaLanguage {
    fun get(): Mode {
        val KEYWORDS = mapOf(
            "keyword" to "abstract assert break case catch class const continue default do else enum extends final finally for goto if implements import instanceof interface native new package private protected public return static strictfp super switch synchronized throw throws transient try volatile while",
            "built_in" to "boolean byte char double float int long short this",
            "keyword_void" to "void",
            "literal" to "true false null"
        )
        
        val STRING = Mode(
            className = "string",
            begin = "\"", 
            end = "\"",
            contains = listOf(
                Mode(className = "variable", begin = "\\\\[btnfr\"'\\\\]") // simple escape
            )
        )
        
        val ANNOTATION = Mode(
            className = "meta",
            begin = "@[A-Za-z0-9_]+",
            end = ""
        )
        
        val NUMBER = Mode(
            className = "number",
            begin = "\\b\\d+(\\.\\d+)?f?\\b",
            end = ""
        )
        
        val CONSTANT = Mode(
            className = "constant",
            begin = "\\b[A-Z_][A-Z0-9_]*\\b",
            end = ""
        )
        
        val FUNCTION = Mode(
            className = "function",
            begin = "\\b(?!if\\b|for\\b|while\\b|switch\\b|catch\\b|synchronized\\b|try\\b|do\\b)\\w+(?=\\s*\\([^)]*\\)\\s*\\{)",
            end = ""
        )
        
        val CLASS_NAME = Mode(
            className = "title",
            begin = "(?<=\\bclass\\s)[A-Za-z_$][\\w$]*",
            end = ""
        )
        
        val EXTENDS_NAME = Mode(
            className = "title",
            begin = "(?<=\\bextends\\s)[A-Za-z_$][\\w$]*",
            end = ""
        )
        
        val NEW_CLASS = Mode(
            className = "title",
            begin = "(?<=\\bnew\\s)[A-Za-z_$][\\w$]*",
            end = ""
        )
        
        val FIELD = Mode(
            className = "field",
            begin = "\\b(?![A-Z_][A-Z0-9_]*\\b)[A-Za-z_$][\\w$]*\\b(?=\\s*(=|;))",
            end = ""
        )

        return Mode(
            className = "java",
            keywords = KEYWORDS,
            contains = listOf(
                Mode(className = "comment", begin = "//", end = "\n"),
                Mode(className = "comment", begin = "/\\*", end = "\\*/"),
                STRING,
                ANNOTATION,
                NUMBER,
                CONSTANT,
                FUNCTION,
                FIELD,
                CLASS_NAME,
                EXTENDS_NAME,
                NEW_CLASS
            )
        )
    }
}
