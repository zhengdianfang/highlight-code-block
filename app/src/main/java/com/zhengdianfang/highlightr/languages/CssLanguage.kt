package com.zhengdianfang.highlightr.languages

import com.zhengdianfang.highlightr.Mode

object CssLanguage {
    fun get(): Mode {
        val KEYWORDS = mapOf(
            "keyword" to "important",
            "selector-tag" to "div span body html p a img ul ol li table tr td th br hr form input button select textarea h1 h2 h3 h4 h5 h6 header footer nav section article aside main video audio canvas iframe svg"
        )

        // Comments
        val COMMENT = Mode(
            className = "comment",
            begin = "/\\*",
            end = "\\*/"
        )

        // Strings
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

        // Hex Colors - Match valid hex codes
        val HEX_COLOR = Mode(
            className = "number",
            begin = "#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})\\b",
            end = ""
        )

        // Numbers and Units
        val NUMBER_WITH_UNIT = Mode(
            className = "number",
            begin = "\\b\\d+(\\.\\d+)?(%|em|rem|px|in|cm|mm|ex|pt|pc|vh|vw|vmin|vmax|deg|rad|grad|turn|s|ms|Hz|kHz|dpi|dpcm|dppx)?\\b",
            end = ""
        )

        // Selectors
        val CLASS_SELECTOR = Mode(
            className = "selector-class",
            begin = "\\.[a-zA-Z0-9_-]+",
            end = ""
        )

        val ID_SELECTOR = Mode(
            className = "selector-id",
            begin = "#[a-zA-Z0-9_-]+",
            end = ""
        )

        val PSEUDO_SELECTOR = Mode(
            className = "variable",
            begin = ":(:)?[a-z-]+",
            end = ""
        )

        val ATTRIBUTE_SELECTOR = Mode(
            className = "variable",
            begin = "\\[",
            end = "\\]",
            contains = listOf(STRING, APOS_STRING)
        )

        // At-rules
        val AT_RULE = Mode(
            className = "keyword",
            begin = "@[a-z-]+",
            end = ""
        )

        // Properties (heuristics: word followed by colon)
        val PROPERTY = Mode(
            className = "attribute",
            begin = "(?<![-a-z])[-a-z]+(?=\\s*:)",
            end = ""
        )

        // Important
        val IMPORTANT = Mode(
            className = "keyword",
            begin = "!important",
            end = ""
        )

        // Functions (url(), rgb(), etc.)
        val FUNCTION = Mode(
            className = "built_in",
            begin = "[a-z-]+\\(",
            end = "\\)",
            contains = listOf(
                STRING,
                APOS_STRING,
                NUMBER_WITH_UNIT,
                HEX_COLOR
            )
        )

        return Mode(
            className = "css",
            keywords = KEYWORDS,
            contains = listOf(
                COMMENT,
                AT_RULE,
                IMPORTANT,
                HEX_COLOR, // Put HEX_COLOR before ID_SELECTOR
                ID_SELECTOR,
                CLASS_SELECTOR,
                PSEUDO_SELECTOR,
                ATTRIBUTE_SELECTOR,
                FUNCTION,
                PROPERTY,
                NUMBER_WITH_UNIT,
                STRING,
                APOS_STRING
            )
        )
    }
}
