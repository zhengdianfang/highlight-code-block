package com.zhengdianfang.highlightr

import com.zhengdianfang.highlightr.model.CodeToken
import com.zhengdianfang.highlightr.model.LanguageDefinition
import com.zhengdianfang.highlightr.model.LexRule
import com.zhengdianfang.highlightr.model.TokenType

class HighlighterEngine {

    fun highlight(code: String, language: LanguageDefinition): List<CodeToken> {
        val tokens = mutableListOf<CodeToken>()
        var index = 0
        val length = code.length

        var currentBlockRule: LexRule.BlockRule? = null
        var blockStartIndex = 0

        while (index < length) {
            val remaining = code.substring(index)
            if (currentBlockRule != null) {
                val endMatch = currentBlockRule.end.find(remaining)
                if (endMatch != null) {
                    val endIndex = index + endMatch.range.last + 1
                    tokens += CodeToken(
                        type = currentBlockRule.type,
                        start = blockStartIndex,
                        end = endIndex
                    )
                    index = endIndex
                    currentBlockRule = null
                    continue
                } else {
                    tokens += CodeToken(
                        type = currentBlockRule.type,
                        start = blockStartIndex,
                        end = length
                    )
                    break
                }
            }

            var matched = false
            for (rule in language.blockRules) {
                val match = rule.begin.find(remaining)
                if (match != null && match.range.first == 0) {
                    currentBlockRule = rule
                    blockStartIndex = index
                    index += match.range.last + 1
                    matched = true
                    break
                }
            }
            if (matched) continue

            val inlineMatch = matchInlineRules(remaining, language.inlineRules)
            if (inlineMatch != null) {
                val (rule, range) = inlineMatch
                val start = index + range.first
                val end = index + range.last + 1
                tokens += CodeToken(rule.type, start, end)
                index = end
                continue
            }

            val ch = code[index]
            if (ch.isWhitespace()) {
                val start = index
                var i = index + 1
                while (i < length && code[i].isWhitespace()) i++
                tokens += CodeToken(TokenType.WHITESPACE, start, i)
                index = i
            } else if (ch.isJavaIdentifierStart()) {
                val start = index
                var i = index + 1
                while (i < length && code[i].isJavaIdentifierPart()) i++
                val ident = code.substring(start, i)
                val type = language.extensions.classifyIdentifier(code, start, i, ident)
                    ?: when {
                        language.keywords.contains(ident) -> TokenType.KEYWORD
                        language.types.contains(ident) -> TokenType.TYPE_NAME
                        language.literals.contains(ident) -> TokenType.LITERAL
                        ident.startsWith("@") -> TokenType.ANNOTATION
                        language.isFunctionName(code, start, i) -> TokenType.FUNCTION_NAME
                        else -> TokenType.IDENTIFIER
                    }
                tokens += CodeToken(type, start, i)
                index = i
            } else {
                tokens += CodeToken(TokenType.PUNCTUATION, index, index + 1)
                index++
            }
        }
        return tokens
    }

    private fun matchInlineRules(
        remaining: String,
        rules: List<LexRule.InlineRule>
    ): Pair<LexRule.InlineRule, IntRange>? {
        var best: Pair<LexRule.InlineRule, IntRange>? = null
        for (rule in rules) {
            val m = rule.pattern.find(remaining) ?: continue
            if (m.range.first != 0) continue
            if (best == null || rule.priority > best.first.priority) {
                best = rule to m.range
            }
        }
        return best
    }
}