package com.zhengdianfang.highlightr

import java.util.ArrayDeque

class HighlightEngine {
    private val languages = mutableMapOf<String, Mode>()

    fun registerLanguage(name: String, mode: Mode) {
        languages[name] = mode
    }

    fun highlight(code: String, languageName: String): Result {
        val language = languages[languageName] ?: throw IllegalArgumentException("Language $languageName not found")
        compile(language)
        
        val emitter = TokenTreeEmitter()
        val modeStack = ArrayDeque<Mode>()
        modeStack.addLast(language)
        
        var index = 0
        
        while (index < code.length) {
            val top = modeStack.last()
            val terminators = top.terminators as? MultiRegex
            
            // Scan for next match
            val matchResult = terminators?.find(code, index)
            
            if (matchResult != null) {
                val matchStart = matchResult.match.range.first
                
                // Process text before the match (potential keywords)
                if (matchStart > index) {
                    val text = code.substring(index, matchStart)
                    processBuffer(text, top, emitter)
                }
                
                val matchText = matchResult.match.value
                
                when (matchResult.type) {
                    MatcherType.END -> {
                        // Handle end of mode
                        if (modeStack.size > 1) {
                            emitter.addText(matchText)
                            modeStack.removeLast()
                            emitter.endScope()
                        } else {
                            // Should not happen for root mode usually, just treat as text
                            emitter.addText(matchText)
                        }
                        index = matchResult.match.range.last + 1
                    }
                    MatcherType.BEGIN -> {
                        val newMode = matchResult.mode!!
                        modeStack.addLast(newMode)
                        emitter.startScope(newMode.className ?: "")
                        emitter.addText(matchText)
                        index = matchResult.match.range.last + 1
                    }
                    MatcherType.ILLEGAL -> {
                        // Abort parsing as per flowchart
                        throw IllegalStateException("Illegal match found: $matchText at index $matchStart")
                    }
                }
            } else {
                // No more matches, process remaining text
                val text = code.substring(index)
                processBuffer(text, top, emitter)
                break
            }
        }
        
        // Close any remaining scopes (except root?)
        // The original code closed all > 1.
        while (modeStack.size > 1) {
            emitter.endScope()
            modeStack.removeLast()
        }
        
        return Result(emitter.getResult() as List<TokenTreeEmitter.Event>, languageName)
    }

    private fun processBuffer(text: String, mode: Mode, emitter: Emitter) {
        if (mode.keywords.isNullOrEmpty()) {
            emitter.addText(text)
            return
        }

        val lexemeRegex = Regex(mode.lexemes)
        var lastIdx = 0
        
        lexemeRegex.findAll(text).forEach { match ->
            // Emit non-lexeme text
            if (match.range.first > lastIdx) {
                emitter.addText(text.substring(lastIdx, match.range.first))
            }
            
            val word = match.value
            val keywordType = mode.compiledKeywords?.get(word)
            
            if (keywordType != null) {
                emitter.startScope(keywordType)
                emitter.addText(word)
                emitter.endScope()
            } else {
                emitter.addText(word)
            }
            
            lastIdx = match.range.last + 1
        }
        
        if (lastIdx < text.length) {
            emitter.addText(text.substring(lastIdx))
        }
    }

    private fun compile(mode: Mode, parent: Mode? = null) {
        if (mode.terminators != null) return // Already compiled
        
        mode.parent = parent
        
        // Compile regexes
        mode.begin?.let { mode.compiledBegin = Regex(it) }
        mode.end?.let { mode.compiledEnd = Regex(it) }
        mode.illegal?.let { mode.compiledIllegal = Regex(it) }
        
        // Compile keywords (Invert map for O(1) lookup)
        if (mode.compiledKeywords == null && !mode.keywords.isNullOrEmpty()) {
            val compiled = mutableMapOf<String, String>()
            mode.keywords.forEach { (type, words) ->
                words.split(" ").forEach { word ->
                    compiled[word] = type
                }
            }
            mode.compiledKeywords = compiled
        }
        
        // Recursively compile children
        mode.contains.forEach { compile(it, mode) }
        
        // Build MultiRegex (The core of the scanner)
        val matchers = mutableListOf<Matcher>()
        
        // 1. End matcher (if not root/parent exists)
        // Check if this mode has an end pattern
        mode.compiledEnd?.let { 
             matchers.add(Matcher(it, MatcherType.END)) 
        }
        
        // 2. Illegal matcher
        mode.compiledIllegal?.let { matchers.add(Matcher(it, MatcherType.ILLEGAL)) }
        
        // 3. Child begin matchers
        mode.contains.forEach { child ->
            child.compiledBegin?.let { matchers.add(Matcher(it, MatcherType.BEGIN, child)) }
        }
        
        mode.terminators = MultiRegex(matchers)
    }
    
    // --- Helper Classes ---

    data class Result(
        val events: List<TokenTreeEmitter.Event>,
        val language: String
    )
    
    enum class MatcherType { BEGIN, END, ILLEGAL }
    
    data class Matcher(val regex: Regex, val type: MatcherType, val mode: Mode? = null)
    
    data class MultiMatchResult(val match: MatchResult, val type: MatcherType, val mode: Mode?)

    /**
     * Helper to simulate a merged regex by finding the earliest match among multiple regexes.
     * This avoids complex regex merging logic while preserving the behavior.
     */
    class MultiRegex(private val matchers: List<Matcher>) {
        fun find(input: CharSequence, startIndex: Int = 0): MultiMatchResult? {
            var bestMatch: MatchResult? = null
            var bestMatcher: Matcher? = null
            
            for (matcher in matchers) {
                val match = matcher.regex.find(input, startIndex)
                if (match != null) {
                    if (bestMatch == null || match.range.first < bestMatch.range.first) {
                        bestMatch = match
                        bestMatcher = matcher
                    }
                }
            }
            
            return if (bestMatch != null && bestMatcher != null) {
                MultiMatchResult(bestMatch, bestMatcher.type, bestMatcher.mode)
            } else {
                null
            }
        }
    }
}
