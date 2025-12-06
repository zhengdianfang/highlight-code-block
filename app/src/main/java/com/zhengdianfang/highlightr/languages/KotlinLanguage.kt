package com.zhengdianfang.highlightr.languages

import com.zhengdianfang.highlightr.LanguageRegistry
import com.zhengdianfang.highlightr.model.LanguageDefinition
import com.zhengdianfang.highlightr.model.LanguageExtensions
import com.zhengdianfang.highlightr.model.LexRule
import com.zhengdianfang.highlightr.model.TokenType

object KotlinLanguage {

    private val KEYWORDS = setOf(
        "fun", "val", "var", "class", "interface", "object", "package", "import",
        "if", "else", "when", "for", "while", "do", "return", "break", "continue",
        "is", "in", "as", "this", "super",
        "try", "catch", "finally", "throw",
        "public", "private", "protected", "internal",
        "abstract", "open", "final", "override", "data", "sealed", "const", "suspend",
        "companion", "where", "external", "expect", "actual"
    )

    private val TYPES = setOf(
        "Int", "Long", "Short", "Byte", "Float", "Double", "Boolean", "Char",
        "String", "Any", "Unit", "Nothing", "List", "MutableList", "Map", "MutableMap"
    )

    private val LITERALS = setOf(
        "null", "true", "false"
    )

    val definition = LanguageDefinition(
        id = "kotlin",
        blockRules = listOf(
            // 多行注释 /* ... */
            LexRule.BlockRule(
                type = TokenType.COMMENT,
                begin = Regex("/\\*"),
                end = Regex("\\*/")
            ),
            // 字符串 "..."
            LexRule.BlockRule(
                type = TokenType.STRING,
                begin = Regex("\""),
                end = Regex("\""),
                escapeSequence = Regex("\\\\.") // 处理 \" 等，后续可用
            )
            // 后续可以增加 """ 三引号字符串
        ),
        inlineRules = listOf(
            // 单行注释 //
            LexRule.InlineRule(
                type = TokenType.COMMENT,
                pattern = Regex("//.*"),
                priority = 10
            ),
            // 数字
            LexRule.InlineRule(
                type = TokenType.NUMBER,
                pattern = Regex("\\b\\d+(\\.\\d+)?([eE][+-]?\\d+)?\\b"),
                priority = 5
            ),
            // 注解
            LexRule.InlineRule(
                type = TokenType.ANNOTATION,
                pattern = Regex("@[A-Za-z_][A-Za-z0-9_]*"),
                priority = 5
            )
        ),
        keywords = KEYWORDS,
        types = TYPES,
        literals = LITERALS,
        extensions = LanguageExtensions(
            classifyIdentifier = { source, start, end, ident ->
                if (isAfterClassLikeKeyword(source, start)) {
                    return@LanguageExtensions TokenType.TYPE_NAME
                }
                if (isInTypePosition(source, start)) {
                    return@LanguageExtensions TokenType.TYPE_NAME
                }
                null
            },
        ),
        isFunctionName = { source, start, end ->
            val before = source.substring(0, start)
            if (before.trimEnd().endsWith("fun")) return@LanguageDefinition true

            var i = end
            while (i < source.length && source[i].isWhitespace()) i++
            if (i < source.length && source[i] == '(') return@LanguageDefinition true

            false
        }
    )

    private fun isAfterClassLikeKeyword(source: String, identStart: Int): Boolean {
        var j = identStart - 1
        while (j >= 0 && source[j].isWhitespace()) j--
        if (j < 0) return false
        val from = (j - 20).coerceAtLeast(0)
        val prefix = source.substring(from, identStart)

        val trimmed = prefix.trimEnd()
        return trimmed.endsWith("class") ||
                trimmed.endsWith("interface") ||
                trimmed.endsWith("object")
    }

    private fun isInTypePosition(source: String, identStart: Int): Boolean {
        var j = identStart - 1
        while (j >= 0 && source[j].isWhitespace()) j--

        if (j < 0) return false
        return source[j] == ':'
    }

    fun register() {
        LanguageRegistry.register(definition)
    }
}