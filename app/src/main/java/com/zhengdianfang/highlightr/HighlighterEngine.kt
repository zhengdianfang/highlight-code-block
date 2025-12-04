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

            // 1. 若在 Block 中，寻找 end
            if (currentBlockRule != null) {
                val endMatch = currentBlockRule.end.find(remaining)
                if (endMatch != null && endMatch.range.first == 0) {
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
                    // 找不到 end，认为直到末尾都是该 Block
                    tokens += CodeToken(
                        type = currentBlockRule.type,
                        start = blockStartIndex,
                        end = length
                    )
                    break
                }
            }

            // 2. 尝试匹配 Block begin
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

            // 3. 尝试匹配 Inline 规则
            val inlineMatch = matchInlineRules(remaining, language.inlineRules)
            if (inlineMatch != null) {
                val (rule, range) = inlineMatch
                val start = index + range.first
                val end = index + range.last + 1
                tokens += CodeToken(rule.type, start, end)
                index = end
                continue
            }

            // 4. 处理普通字符/空白/标识符
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
                val type = when {
                    language.keywords.contains(ident) -> TokenType.KEYWORD
                    language.types.contains(ident) -> TokenType.TYPE_NAME
                    ident.startsWith("@") -> TokenType.ANNOTATION
                    else -> TokenType.IDENTIFIER
                }
                tokens += CodeToken(type, start, i)
                index = i
            } else {
                // 符号或其他
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
            if (m.range.first != 0) continue // 必须从当前位置开始
            if (best == null || rule.priority > best.first.priority) {
                best = rule to m.range
            }
        }
        return best
    }
}