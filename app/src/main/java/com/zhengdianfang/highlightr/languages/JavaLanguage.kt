package com.zhengdianfang.highlightr.languages

import com.zhengdianfang.highlightr.LanguageRegistry
import com.zhengdianfang.highlightr.model.LanguageDefinition
import com.zhengdianfang.highlightr.model.LanguageExtensions
import com.zhengdianfang.highlightr.model.LexRule
import com.zhengdianfang.highlightr.model.TokenType

object JavaLanguage {
    const val LANGUAGE_NAME = "java"

    // Java 常见关键字
    private val KEYWORDS = setOf(
        "abstract", "assert", "boolean", "break", "byte",
        "case", "catch", "char", "class", "const",
        "continue", "default", "do", "double", "else",
        "enum", "extends", "final", "finally", "float",
        "for", "goto", "if", "implements", "import",
        "instanceof", "int", "interface", "long",
        "native", "new", "package", "private", "protected",
        "public", "return", "short", "static", "strictfp",
        "super", "switch", "synchronized", "this",
        "throw", "throws", "transient", "try", "void",
        "volatile", "while"
    )

    // Java 常用内置类型（用于 TYPE_NAME 高亮）
    private val TYPES = setOf(
        "String", "Object", "Integer", "Long", "Short",
        "Byte", "Float", "Double", "Boolean", "Character",
        "List", "ArrayList", "LinkedList", "Map", "HashMap",
        "Set", "HashSet", "Queue", "Deque", "Optional"
    )

    private val LITERALS = setOf(
        "null", "true", "false"
    )

    val definition = LanguageDefinition(
        id = "java",
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
                escapeSequence = Regex("\\\\.")
            ),
            // 字符字面量 'a'
            LexRule.BlockRule(
                type = TokenType.STRING,
                begin = Regex("'"),
                end = Regex("'"),
                escapeSequence = Regex("\\\\.")
            )
        ),
        literals = LITERALS,
        inlineRules = listOf(
            // 单行注释 //
            LexRule.InlineRule(
                type = TokenType.COMMENT,
                pattern = Regex("//.*"),
                priority = 10
            ),

            // 数字字面量
            LexRule.InlineRule(
                type = TokenType.NUMBER,
                pattern = Regex("\\b\\d+(\\.\\d+)?([eE][+-]?\\d+)?\\b"),
                priority = 5
            ),

            // 注解 @Something
            LexRule.InlineRule(
                type = TokenType.ANNOTATION,
                pattern = Regex("@[A-Za-z_][A-Za-z0-9_]*"),
                priority = 5
            )
        ),
        keywords = KEYWORDS,
        types = TYPES,

        extensions = LanguageExtensions(

            // ---------------------------
            // ⭐ Java 自定义类型识别规则
            // ---------------------------
            classifyIdentifier = { source, start, end, ident ->

                // 内置关键字 / 类型：不抢，交给默认逻辑
                if (KEYWORDS.contains(ident) || TYPES.contains(ident)) {
                    return@LanguageExtensions null
                }

                if (isAfterKeyword(source, start, "class") ||
                    isAfterKeyword(source, start, "interface") ||
                    isAfterKeyword(source, start, "enum") ||
                    isAfterKeyword(source, start, "new") ||
                    isAfterKeyword(source, start, "static")
                ) {
                    return@LanguageExtensions TokenType.TYPE_NAME
                }

                if (isInJavaTypePosition(source, start)) {
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

            if (isMethodDeclaration(source, start)) {
                return@LanguageDefinition true
            }

            false
        }
    )

    private fun isAfterKeyword(source: String, identStart: Int, keyword: String): Boolean {
        var j = identStart - 1
        while (j >= 0 && source[j].isWhitespace()) j--
        if (j < 0) return false

        val from = (j - keyword.length - 2).coerceAtLeast(0)
        val prefix = source.substring(from, identStart)
        val trimmed = prefix.trimEnd()

        val isAfterExactKeyword = trimmed.endsWith(keyword) &&
            (trimmed.length == keyword.length || !trimmed[trimmed.length - keyword.length - 1].isLetterOrDigit())

        if (isAfterExactKeyword && keyword == "static") {
            return !isReturnTypeAfterStatic(source, identStart)
        }

        return isAfterExactKeyword
    }

    private fun isReturnTypeAfterStatic(source: String, typeStart: Int): Boolean {
        // Skip the type identifier
        var i = typeStart
        while (i < source.length && source[i].isJavaIdentifierPart()) i++
        // Skip whitespace
        while (i < source.length && source[i].isWhitespace()) i++
        
        // Check if there's another identifier followed by '('
        if (i < source.length && source[i].isJavaIdentifierStart()) {
            // Skip the function name
            while (i < source.length && source[i].isJavaIdentifierPart()) i++
            // Skip whitespace
            while (i < source.length && source[i].isWhitespace()) i++
            // Check for opening parenthesis
            if (i < source.length && source[i] == '(') {
                return true
            }
        }
        return false
    }

    private fun isInJavaTypePosition(source: String, identStart: Int): Boolean {
        var j = identStart - 1
        while (j >= 0 && source[j].isWhitespace()) j--
        if (j < 0) return false

        val prev = source[j]

        if (prev == '<') return true
        if (prev == ',') return true
        if (prev == '(') return true
        if (prev == '[') return true
        return prev == ' ' || prev == '\n' || prev == '\t'
    }

    private fun isMethodDeclaration(source: String, identStart: Int): Boolean {
        var j = identStart - 1
        while (j >= 0 && source[j].isWhitespace()) j--
        if (j < 0) return false
        
        // Check if this is after a return type (i.e., we're at a function name)
        // Look ahead to see if this identifier is followed by '('
        var i = identStart
        while (i < source.length && source[i].isJavaIdentifierPart()) i++
        while (i < source.length && source[i].isWhitespace()) i++
        if (i < source.length && source[i] == '(') {
            return true
        }
        
        return false
    }

    fun register() {
        LanguageRegistry.register(definition)
    }
}