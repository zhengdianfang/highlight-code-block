package com.zhengdianfang.highlightr.languages

import com.zhengdianfang.highlightr.Mode

object PythonLanguage {
    fun get(): Mode {
        val KEYWORDS = mapOf(
            "keyword" to "and as assert async await break class continue def del elif else except exec finally for from global if import in is lambda nonlocal not or pass print raise return try while with yield",
            "built_in" to "abs all any ascii bin bool breakpoint bytearray bytes callable chr classmethod compile complex delattr dict dir divmod enumerate eval exec filter float format frozenset getattr globals hasattr hash help hex id input int isinstance issubclass iter len list locals map max memoryview min next object oct open ord pow print property range repr reversed round set setattr slice sorted staticmethod str sum super tuple type vars zip",
            "literal" to "__debug__ Ellipsis False None NotImplemented True"
        )

        val PROMPT = Mode(
            className = "meta", 
            begin = "^>>> ",
            end = ""
        )

        val STRING_ESCAPE = Mode(begin = "\\\\[\\s\\S]", end = "")
        
        val STRING_VARIANTS = listOf(
            Mode(className = "string", begin = "\"\"\"", end = "\"\"\"", contains = listOf(STRING_ESCAPE)),
            Mode(className = "string", begin = "'''", end = "'''", contains = listOf(STRING_ESCAPE)),
            Mode(className = "string", begin = "\"", end = "\"", contains = listOf(STRING_ESCAPE)),
            Mode(className = "string", begin = "'", end = "'", contains = listOf(STRING_ESCAPE))
        )

        val NUMBER_VARIANTS = listOf(
            Mode(className = "number", begin = "\\b0[bB](?:_?[01])+[lL]?\\b", end = ""),
            Mode(className = "number", begin = "\\b0[oO](?:_?[0-7])+[lL]?\\b", end = ""),
            Mode(className = "number", begin = "\\b0[xX](?:_?[0-9a-fA-F])+[lL]?\\b", end = ""),
            Mode(className = "number", begin = "\\b(?:[1-9](?:_?\\d)*|0+)(?:\\.(?:\\d(?:_?\\d)*)?)?(?:[eE][+-]?\\d(?:_?\\d)*)?[jJ]?\\b", end = ""),
            Mode(className = "number", begin = "\\.\\d(?:_?\\d)*(?:[eE][+-]?\\d(?:_?\\d)*)?[jJ]?\\b", end = ""),
            Mode(className = "number", begin = "\\b(?:[1-9](?:_?\\d)*|0+)[lL]?\\b", end = "")
        )
        
        val COMMENT = Mode(
            className = "comment",
            begin = "#",
            end = "\n"
        )

        val DECORATOR = Mode(
            className = "meta",
            begin = "@",
            end = "\n"
        )

        val FUNCTION = Mode(
            className = "keyword",
            begin = "\\bdef\\s+",
            end = ":",
            contains = listOf(
                Mode(
                    className = "function",
                    begin = "\\b[a-zA-Z_]\\w*",
                    end = ""
                ),
                Mode(
                    className = "params",
                    begin = "\\(",
                    end = "\\)",
                    contains = STRING_VARIANTS + NUMBER_VARIANTS + listOf(
                        Mode(className = "built_in", begin = "\\bself\\b", end = "")
                    )
                )
            )
        )

        val CLASS = Mode(
            className = "keyword",
            begin = "\\bclass\\s+",
            end = ":",
            contains = listOf(
                Mode(
                    className = "title",
                    begin = "\\b[a-zA-Z_]\\w*",
                    end = ""
                ),
                Mode(
                    className = "params",
                    begin = "\\(",
                    end = "\\)",
                    contains = listOf(
                        Mode(className = "title", begin = "\\b[a-zA-Z_]\\w*", end = "")
                    )
                )
            )
        )

        return Mode(
            className = "python",
            keywords = KEYWORDS,
            contains = listOf(
                PROMPT,
                COMMENT,
                DECORATOR,
                FUNCTION,
                CLASS
            ) + STRING_VARIANTS + NUMBER_VARIANTS
        )
    }
}
