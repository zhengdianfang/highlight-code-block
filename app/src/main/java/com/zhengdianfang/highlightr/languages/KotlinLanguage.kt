package com.zhengdianfang.highlightr.languages

import com.zhengdianfang.highlightr.LanguageRegistry
import com.zhengdianfang.highlightr.model.LanguageDefinition
import com.zhengdianfang.highlightr.model.LexRule
import com.zhengdianfang.highlightr.model.TokenType

object KotlinLanguage {

    private val KEYWORDS = setOf(
        "fun", "val", "var", "class", "interface", "object", "package", "import",
        "if", "else", "when", "for", "while", "do", "return", "break", "continue",
        "is", "in", "as", "this", "super", "null", "true", "false",
        "try", "catch", "finally", "throw",
        "public", "private", "protected", "internal",
        "abstract", "open", "final", "override", "data", "sealed", "const", "suspend",
        "companion", "where"
    )

    private val TYPES = setOf(
        "Int", "Long", "Short", "Byte", "Float", "Double", "Boolean", "Char",
        "String", "Any", "Unit", "Nothing", "List", "MutableList", "Map", "MutableMap"
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
        types = TYPES
    )

    fun register() {
        LanguageRegistry.register(definition)
    }
}