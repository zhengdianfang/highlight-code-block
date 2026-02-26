package com.zhengdianfang.highlightr.languages

import com.zhengdianfang.highlightr.Mode

object SqlLanguage {
    fun get(): Mode {
        val KEYWORDS = mapOf(
            "keyword" to "select from where insert update delete create table drop alter index view as and or not in is null like between order by group having limit offset join on left right inner outer union all distinct values into set database grant revoke primary key foreign references default check constraint unique true false case when then else end",
            "built_in" to "int varchar current_timestamp timestamp boolean",
            "literal" to "unknown"
        )

        val KEYWORD_MODE = Mode(
            className = "keyword",
            begin = "(?i)\\b(${KEYWORDS["keyword"]!!.replace(" ", "|")})\\b",
            end = ""
        )

        val BUILT_IN_MODE = Mode(
            className = "built_in",
            begin = "(?i)\\b(${KEYWORDS["built_in"]!!.replace(" ", "|")})\\b",
            end = ""
        )

        val LITERAL_MODE = Mode(
            className = "literal",
            begin = "(?i)\\b(${KEYWORDS["literal"]!!.replace(" ", "|")})\\b",
            end = ""
        )

        val COMMENT_MODE = Mode(
            className = "comment",
            begin = "--",
            end = "\n"
        )

        val BLOCK_COMMENT_MODE = Mode(
            className = "comment",
            begin = "/\\*",
            end = "\\*/"
        )

        val STRING_SINGLE = Mode(
            className = "string",
            begin = "'",
            end = "'"
        )
        
        val STRING_DOUBLE = Mode(
            className = "string",
            begin = "\"",
            end = "\""
        )

        val NUMBER = Mode(
            className = "number",
            begin = "\\b\\d+(\\.\\d+)?\\b",
            end = ""
        )

        return Mode(
            className = "sql",
            keywords = KEYWORDS,
            contains = listOf(
                COMMENT_MODE,
                BLOCK_COMMENT_MODE,
                STRING_SINGLE,
                STRING_DOUBLE,
                NUMBER,
                KEYWORD_MODE,
                BUILT_IN_MODE,
                LITERAL_MODE
            )
        )
    }
}
