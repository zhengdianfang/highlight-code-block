package com.zhengdianfang.highlightr.languages

import com.zhengdianfang.highlightr.HighlightEngine
import com.zhengdianfang.highlightr.TokenTreeEmitter
import org.junit.Test
import org.junit.Assert.*

class PhpLanguageTest {

    @Test
    fun testPhpHighlightingDebug() {
        val engine = HighlightEngine()
        engine.registerLanguage("php", PhpLanguage.get())
        
        val code = """
            <?php
            namespace App;
            class User {
                public function index() {
                    return 1;
                }
            }
            ${'$'}x = 10;
        """.trimIndent()
        
        val result = engine.highlight(code, "php")
        
        println("Events:")
        result.events.forEach { event ->
            when (event) {
                is TokenTreeEmitter.Event.Start -> println("Start: ${event.scope}")
                is TokenTreeEmitter.Event.End -> println("End")
                is TokenTreeEmitter.Event.Text -> println("Text: '${event.text.replace("\n", "\\n")}'")
            }
        }
        
        val foundScopes = result.events.filterIsInstance<TokenTreeEmitter.Event.Start>().map { it.scope }.distinct()
        println("Found scopes: $foundScopes")
        
        assertTrue("Should have keyword scope", foundScopes.contains("keyword"))
    }
}
