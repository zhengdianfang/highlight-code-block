package com.zhengdianfang.highlightr.languages

import com.zhengdianfang.highlightr.Mode

object ShellLanguage {
    fun get(): Mode {
        val KEYWORDS = mapOf(
            "keyword" to "if then else elif fi for while in do done case esac function",
            "built_in" to "break cd continue eval exec exit export getopts hash pwd readonly return shift test times trap umask unset alias bind builtin caller command declare echo enable help let local logout mapfile printf read readarray source type typeset ulimit unalias wait ls cat grep awk sed tar chmod chown kill ps top df du",
            "literal" to "true false"
        )

        val KEYWORD_MODE = Mode(
            className = "keyword",
            begin = "\\b(${KEYWORDS["keyword"]!!.replace(" ", "|")})\\b",
            end = ""
        )

        val BUILT_IN_MODE = Mode(
            className = "built_in",
            begin = "\\b(${KEYWORDS["built_in"]!!.replace(" ", "|")})\\b",
            end = ""
        )

        val LITERAL_MODE = Mode(
            className = "literal",
            begin = "\\b(${KEYWORDS["literal"]!!.replace(" ", "|")})\\b",
            end = ""
        )

        val COMMENT_MODE = Mode(
            className = "comment",
            begin = "(?<=^|\\s)#",
            end = "\n"
        )

        val VARIABLE = Mode(
            className = "template-variable",
            begin = "\\\$[a-zA-Z0-9_]+",
            end = ""
        )

        val BRACED_VARIABLE = Mode(
            className = "template-variable",
            begin = "\\\$\\{[a-zA-Z0-9_]+\\}",
            end = ""
        )

        val STRING_DOUBLE = Mode(
            className = "string",
            begin = "\"",
            end = "\"",
            contains = listOf(
                Mode(begin = "\\\\."),
                VARIABLE,
                BRACED_VARIABLE
            )
        )

        val STRING_SINGLE = Mode(
            className = "string",
            begin = "'",
            end = "'"
        )

        val FUNCTION_DEFINITION = Mode(
            className = "function",
            begin = "[a-zA-Z0-9_]+\\s*\\(\\)",
            end = ""
        )

        val SHEBANG = Mode(
            className = "meta",
            begin = "^#!",
            end = "\n"
        )

        val NUMBER = Mode(
            begin = "\\b\\d+(\\.\\d+)?\\b",
            end = ""
        )

        return Mode(
            className = "shell",
            keywords = KEYWORDS,
            contains = listOf(
                SHEBANG,
                COMMENT_MODE,
                STRING_DOUBLE,
                STRING_SINGLE,
                FUNCTION_DEFINITION,
                VARIABLE,
                BRACED_VARIABLE,
                NUMBER,
                KEYWORD_MODE,
                BUILT_IN_MODE,
                LITERAL_MODE
            )
        )
    }
}
