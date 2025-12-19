package com.zhengdianfang.highlightr.languages

import com.zhengdianfang.highlightr.LanguageRegistry
import com.zhengdianfang.highlightr.model.LanguageDefinition
import com.zhengdianfang.highlightr.model.LanguageExtensions
import com.zhengdianfang.highlightr.model.LexRule
import com.zhengdianfang.highlightr.model.TokenType

object JavaScriptLanguage {

    const val LANGUAGE_NAME = "javascript"

    // 常见 JS 关键字（不要求完全覆盖，后面慢慢补都行）
    private val KEYWORDS = setOf(
        "break", "case", "catch", "class", "const", "continue",
        "debugger", "default", "delete", "do", "else", "export",
        "extends", "finally", "for", "function", "if", "import",
        "in", "instanceof", "new", "return", "super", "switch",
        "this", "throw", "try", "typeof", "var", "let", "void",
        "while", "with", "yield", "await", "async", "from", "as"
    )

    // 一些内置“类型名”/构造器，用 TYPE_NAME 高亮
    private val TYPES = setOf(
        "Number", "String", "Boolean", "Object", "Array",
        "Promise", "Map", "Set", "WeakMap", "WeakSet",
        "Date", "RegExp", "Error", "TypeError", "SyntaxError",
        "RangeError", "EvalError", "Function", "Symbol", "BigInt"
    )

    private val LITERALS = setOf(
        "null", "true", "false"
    )

    val definition = LanguageDefinition(
        id = "javascript",
        blockRules = listOf(
            // 多行注释 /* ... */
            LexRule.BlockRule(
                type = TokenType.COMMENT,
                begin = Regex("/\\*"),
                end = Regex("\\*/")
            ),
            // 双引号字符串 "..."
            LexRule.BlockRule(
                type = TokenType.STRING,
                begin = Regex("\""),
                end = Regex("\""),
                escapeSequence = Regex("\\\\.")  // \" 等
            ),
            // 单引号字符串 '...'
            LexRule.BlockRule(
                type = TokenType.STRING,
                begin = Regex("'"),
                end = Regex("'"),
                escapeSequence = Regex("\\\\.")  // \' 等
            ),
            // 模板字符串 `...` （先不处理 ${} 插值，只当普通字符串块）
            LexRule.BlockRule(
                type = TokenType.STRING,
                begin = Regex("`"),
                end = Regex("`")
            )
        ),
        inlineRules = listOf(
            LexRule.InlineRule(
                type = TokenType.COMMENT,
                pattern = Regex("//.*"),
                priority = 10
            ),
            LexRule.InlineRule(
                type = TokenType.NUMBER,
                pattern = Regex("\\b\\d+(\\.\\d+)?([eE][+-]?\\d+)?\\b"),
                priority = 5
            ),
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
                if (KEYWORDS.contains(ident) || TYPES.contains(ident)) {
                    return@LanguageExtensions null
                }

                if (isAfterKeyword(source, start, "class") ||
                    isAfterKeyword(source, start, "extends") ||
                    isAfterKeyword(source, start, "new")
                ) {
                    return@LanguageExtensions TokenType.TYPE_NAME
                }
                null
            },
        ),
        isFunctionName = { source, start, end ->
            var i = end
            while (i < source.length && source[i].isWhitespace()) i++
            if (i < source.length && source[i] == '(') {
                return@LanguageDefinition true
            }
            var j = start - 1
            while (j >= 0 && source[j].isWhitespace()) j--
            if (j >= "function".length - 1) {
                val maybeFunction = source.substring(j - "function".length + 1, j + 1)
                if (maybeFunction == "function") {
                    return@LanguageDefinition true
                }
            }
            false
        }

    )

    private fun isAfterKeyword(source: String, identStart: Int, keyword: String): Boolean {
        // 向前跳过空白
        var j = identStart - 1
        while (j >= 0 && source[j].isWhitespace()) j--
        if (j < 0) return false

        // 从 ident 前面往前截一小段，看是否以关键字结尾
        val from = (j - keyword.length - 2).coerceAtLeast(0)
        val prefix = source.substring(from, identStart)
        val trimmed = prefix.trimEnd()
        return trimmed.endsWith(keyword)
    }

    fun register() {
        LanguageRegistry.register(definition)
    }
}