package com.zhengdianfang.highlightr.model

data class CodeToken(
    val type: TokenType,
    val start: Int, // inclusive
    val end: Int    // exclusive
)

enum class TokenType {
        KEYWORD,
        IDENTIFIER,
        STRING,
        NUMBER,
        COMMENT,
        ANNOTATION,
        TYPE_NAME,
        LITERAL,
        FUNCTION_NAME,
        OPERATOR,
        PUNCTUATION,
        WHITESPACE,
        PLAIN
}

data class TextStyle(
    val color: Int,
    val bold: Boolean = false,
    val italic: Boolean = false
)

data class CodeTheme(
    val defaultStyle: TextStyle,
    val styleMap: Map<TokenType, TextStyle>
) {
    fun styleFor(type: TokenType): TextStyle =
        styleMap[type] ?: defaultStyle
}

sealed class LexRule {

    data class BlockRule(
        val type: TokenType,
        val begin: Regex,
        val end: Regex,
        val allowsNesting: Boolean = false,
        val escapeSequence: Regex? = null // 处理 \" 之类
    ) : LexRule()

    data class InlineRule(
        val type: TokenType,
        val pattern: Regex,
        val priority: Int = 0 // 冲突时可以按优先级决策
    ) : LexRule()
}

data class LanguageDefinition(
    val id: String,
    val blockRules: List<LexRule.BlockRule>,
    val inlineRules: List<LexRule.InlineRule>,
    val keywords: Set<String> = emptySet(),
    val types: Set<String> = emptySet(),
    val literals: Set<String> = emptySet(),
    val isFunctionName: (source: String, start: Int, end: Int) -> Boolean = { _, _, _ -> false }
)
