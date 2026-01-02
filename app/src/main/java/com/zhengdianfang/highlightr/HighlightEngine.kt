package com.zhengdianfang.highlightr

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
            
            val candidates = mutableListOf<Candidate>()
            
            // 1. End of current mode (unless it's the root)
            if (modeStack.size > 1) {
                top.compiledEnd?.find(code, index)?.let {
                    candidates.add(Candidate(it, MatcherType.END, top))
                }
            }
            
            // 2. Begin of children
            top.contains.forEach { child ->
                child.compiledBegin?.find(code, index)?.let {
                    candidates.add(Candidate(it, MatcherType.BEGIN, child))
                }
            }
            
            val winner = candidates.minByOrNull { it.match.range.first }
            
            if (winner != null) {
                val matchStart = winner.match.range.first
                
                // Process text before the match (potential keywords)
                if (matchStart > index) {
                    val text = code.substring(index, matchStart)
                    processBuffer(text, top, emitter)
                }
                
                if (winner.type == MatcherType.END) {
                    emitter.addText(winner.match.value)
                    modeStack.removeLast()
                    emitter.endScope()
                    index = winner.match.range.last + 1
                } else if (winner.type == MatcherType.BEGIN) {
                    val newMode = winner.mode!!
                    modeStack.addLast(newMode)
                    emitter.startScope(newMode.className ?: "")
                    emitter.addText(winner.match.value)
                    index = winner.match.range.last + 1
                }
            } else {
                // No more matches, consume rest
                val text = code.substring(index)
                processBuffer(text, top, emitter)
                break
            }
        }
        
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

        // Simple keyword matching: split by word boundaries and check map
        // This is a naive implementation. highlight.js does this more robustly.
        val lexemeRegex = Regex(mode.lexemes) 
        var lastIdx = 0
        
        lexemeRegex.findAll(text).forEach { match ->
            // Emit non-lexeme text
            if (match.range.first > lastIdx) {
                emitter.addText(text.substring(lastIdx, match.range.first))
            }
            
            val word = match.value
            val keywordType = findKeyword(word, mode)
            
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

    private fun findKeyword(word: String, mode: Mode): String? {
        // keywords is Map<String, String> where key is type ("keyword") and value is space-separated words
        // We need to invert this structure for fast lookup or iterate.
        // For MVP, iterate.
        mode.keywords?.forEach { (type, words) ->
            if (words.split(" ").contains(word)) {
                return type
            }
        }
        return null
    }

    private data class Candidate(val match: MatchResult, val type: MatcherType, val mode: Mode?)
    private enum class MatcherType { BEGIN, END }

    private fun compile(mode: Mode) {
        if (mode.compiledBegin != null) return
        
        mode.begin?.let { mode.compiledBegin = Regex(it) }
        mode.end?.let { mode.compiledEnd = Regex(it) } // Note: end regex might need back-references logic, ignoring for now
        
        mode.contains.forEach { compile(it) }
    }
    
    data class Result(
        val events: List<TokenTreeEmitter.Event>,
        val language: String
    )
}
