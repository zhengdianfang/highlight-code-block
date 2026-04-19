package com.zhengdianfang.highlightr.languages

import com.zhengdianfang.highlightr.Mode

object TypeScriptLanguage {
    fun get(): Mode {
        val KEYWORDS = mapOf(
            "keyword" to "abstract any as asserts async await break case catch class const constructor continue declare default delete do else enum export extends false finally for from get if implements import in infer instanceof interface is keyof let module namespace never new null of override package private protected public readonly return require set static super switch this throw true try type typeof undefined unique unknown var void while with yield",
            "built_in" to "Array ArrayBuffer Boolean DataView Date Error EvalError Float32Array Float64Array Function Generator GeneratorFunction Infinity Int16Array Int32Array Int8Array Intl JSON Map Math NaN Number Object Promise Proxy RangeError ReferenceError Reflect RegExp Set String Symbol SyntaxError TypeError URIError Uint16Array Uint32Array Uint8Array Uint8ClampedArray WeakMap WeakSet WeakRef console document window localStorage sessionStorage eval isFinite isNaN parseFloat parseInt decodeURI decodeURIComponent encodeURI encodeURIComponent",
            "literal" to "true false null undefined NaN Infinity"
        )

        val NUMBER = Mode(
            className = "number",
            begin = "\\b(0[xX][a-fA-F0-9]+|0[oO][0-7]+|0[bB][01]+|(\\d+(\\.\\d*)?|\\.\\d+)([eE][-+]?\\d+)?)n?\\b",
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

        val DECORATOR = Mode(
            className = "meta",
            begin = "@[a-zA-Z_$][\\w$]*",
            end = ""
        )

        // Type annotations after ':' — matches built-in and user-defined types
        val TYPE_ANNOTATION = Mode(
            className = "type",
            begin = "(?<=:\\s{0,10})(string|number|boolean|void|never|any|unknown|null|undefined|object|symbol|bigint)\\b",
            end = ""
        )

        val GENERIC_TYPE = Mode(
            className = "type",
            begin = "<[a-zA-Z_$][\\w$, ]*>",
            end = ""
        )

        val CLASS_DEFINITION = Mode(
            className = "title",
            begin = "(?<=\\bclass\\s)[a-zA-Z_$][\\w$]*",
            end = ""
        )

        val INTERFACE_DEFINITION = Mode(
            className = "title",
            begin = "(?<=\\binterface\\s)[a-zA-Z_$][\\w$]*",
            end = ""
        )

        val TYPE_ALIAS = Mode(
            className = "title",
            begin = "(?<=\\btype\\s)[a-zA-Z_$][\\w$]*",
            end = ""
        )

        val ENUM_DEFINITION = Mode(
            className = "title",
            begin = "(?<=\\benum\\s)[a-zA-Z_$][\\w$]*",
            end = ""
        )

        val FUNCTION_DEFINITION = Mode(
            className = "function",
            begin = "(?<=\\bfunction\\s)[a-zA-Z_$][\\w$]*",
            end = ""
        )

        val EXTENDS_DEFINITION = Mode(
            className = "title",
            begin = "(?<=\\bextends\\s)[a-zA-Z_$][\\w$]*",
            end = ""
        )

        val IMPLEMENTS_DEFINITION = Mode(
            className = "title",
            begin = "(?<=\\bimplements\\s)[a-zA-Z_$][\\w$]*",
            end = ""
        )

        val BUILT_INS = KEYWORDS["built_in"]?.replace(" ", "|") ?: ""
        val USER_TYPE = Mode(
            className = "title",
            begin = "\\b(?!($BUILT_INS)\\b)[A-Z][A-Za-z0-9_]*\\b",
            end = ""
        )

        val FUNCTION_CALL = Mode(
            className = "function",
            begin = "[a-zA-Z_$][\\w$]*(?=\\s*(<[^>]*>\\s*)?\\()",
            end = ""
        )

        val REGEX = Mode(
            className = "string",
            begin = "/(?![*+?])(?:[^\\r\\n\\[/\\\\]|\\\\.|(?:\\[(?:[^\\r\\n\\]\\\\]|\\\\.)*\\]))+/[gimsuy]*",
            end = ""
        )

        return Mode(
            className = "typescript",
            keywords = KEYWORDS,
            contains = listOf(
                COMMENT_SINGLE,
                COMMENT_BLOCK,
                DECORATOR,
                TEMPLATE_STRING,
                STRING,
                DOUBLE_QUOTE_STRING,
                NUMBER,
                TYPE_ANNOTATION,
                GENERIC_TYPE,
                CLASS_DEFINITION,
                INTERFACE_DEFINITION,
                TYPE_ALIAS,
                ENUM_DEFINITION,
                FUNCTION_DEFINITION,
                EXTENDS_DEFINITION,
                IMPLEMENTS_DEFINITION,
                USER_TYPE,
                FUNCTION_CALL,
                REGEX
            )
        )
    }
}
