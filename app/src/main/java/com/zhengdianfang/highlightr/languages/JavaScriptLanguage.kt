package com.zhengdianfang.highlightr.languages

import com.zhengdianfang.highlightr.Mode

object JavascriptLanguage {
    fun get(): Mode {
        val KEYWORDS = mapOf(
            "keyword" to "in of if for while finally var new do return void else break catch instanceof with throw case default try this switch continue typeof delete let const yield import export from as async await debugger super function class",
            "built_in" to "eval isFinite isNaN parseFloat parseInt decodeURI decodeURIComponent encodeURI encodeURIComponent escape unescape Object Function Boolean Symbol Error EvalError InternalError RangeError ReferenceError SyntaxError TypeError URIError Number Math Date String RegExp Array Float32Array Float64Array Int16Array Int32Array Int8Array Uint16Array Uint32Array Uint8Array Uint8ClampedArray ArrayBuffer DataView JSON Promise Generator GeneratorFunction Reflect Proxy Intl arguments console window document localStorage sessionStorage",
            "literal" to "true false null undefined NaN Infinity"
        )
        // Note: 'function', 'class', 'extends' are handled by specific modes to allow styling of following names

        val NUMBER = Mode(
            className = "number",
            begin = "\\b(0[xX][a-fA-F0-9]+|(\\d+(\\.\\d*)?|\\.\\d+)([eE][-+]?\\d+)?)\\b",
            end = ""
        )

        val STRING = Mode(
            className = "string",
            begin = "'",
            end = "'",
            contains = listOf(
                Mode(className = "variable", begin = "\\\\[\\\\btnfr\"']", end = "")
            )
        )

        val DOUBLE_QUOTE_STRING = Mode(
            className = "string",
            begin = "\"",
            end = "\"",
            contains = listOf(
                Mode(className = "variable", begin = "\\\\[\\\\btnfr\"']", end = "")
            )
        )

        val TEMPLATE_STRING = Mode(
            className = "string",
            begin = "`",
            end = "`",
            contains = listOf(
                Mode(
                    className = "template-variable",
                    begin = "\\$\\{",
                    end = "\\}",
                    contains = emptyList()
                ),
                Mode(
                    className = "template-variable",
                    begin = "\\$[a-zA-Z_][\\w$]*",
                    end = ""
                )
            )
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
        
        // Matches the name of a function definition or class definition
        val TITLE_MODE = Mode(
            className = "title",
            begin = "[a-zA-Z_$][\\w$]*",
            end = ""
        )
        
        val FUNCTION_NAME_MODE = Mode(
            className = "function",
            begin = "[a-zA-Z_$][\\w$]*",
            end = ""
        )

        val CLASS_DEFINITION = Mode(
            className = "title",
            begin = "(?<=\\bclass\\s)[a-zA-Z_$][\\w$]*",
            end = ""
        )
        
        val FUNCTION_DEFINITION = Mode(
            className = "function",
            begin = "(?<=\\bfunction\\s)[a-zA-Z_$][\\w$]*",
            end = ""
        )
        
        val FUNCTION_DEFINITION_ALT = Mode(
            className = "function",
            begin = "(?<=\\bfunction\\s\\s)[a-zA-Z_$][\\w$]*",
            end = ""
        )
        
        val EXTENDS_DEFINITION = Mode(
            className = "title",
            begin = "(?<=\\bextends\\s)[a-zA-Z_$][\\w$]*",
            end = ""
        )
        
        val BUILT_INS = KEYWORDS["built_in"]?.replace(" ", "|") ?: ""
        val TYPE_MODE = Mode(
            className = "title",
            begin = "\\b(?!($BUILT_INS)\\b)[A-Z][A-Za-z0-9_]*\\b",
            end = ""
        )

        // Matches function calls like foo()
        val FUNCTION_CALL = Mode(
            className = "function",
            begin = "[a-zA-Z_$][\\w$]*(?=\\()",
            end = ""
        )
        
        // Simplified Regex literal
        val REGEX = Mode(
            className = "string",
            begin = "/(?![*+?])(?:[^\\r\\n\\[/\\\\]|\\\\.|(?:\\[(?:[^\\r\\n\\]\\\\]|\\\\.)*\\]))+/[gimuy]*",
            end = ""
        )

        return Mode(
            className = "javascript",
            keywords = KEYWORDS,
            contains = listOf(
                COMMENT_SINGLE,
                COMMENT_BLOCK,
                TEMPLATE_STRING,
                STRING,
                DOUBLE_QUOTE_STRING,
                NUMBER,
                CLASS_DEFINITION,
                FUNCTION_DEFINITION,
                FUNCTION_DEFINITION_ALT,
                EXTENDS_DEFINITION,
                TYPE_MODE,
                FUNCTION_CALL,
                REGEX
            )
        )
    }
}
