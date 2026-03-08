package com.zhengdianfang.highlightr.languages

import com.zhengdianfang.highlightr.HighlightEngine
import com.zhengdianfang.highlightr.TokenTreeEmitter
import org.junit.Test
import org.junit.Assert.*

class GoLanguageTest {

    @Test
    fun testGoHighlighting() {
        val engine = HighlightEngine()
        engine.registerLanguage("go", GoLanguage.get())
        
        val code = """
            // Loop
            for i := 0; i < 5; i++ {
                if i%2 == 0 {
                    fmt.Println(i, "is even")
                }
            }
        """.trimIndent()
        
        val result = engine.highlight(code, "go")
        
        result.events.forEach { println(it) }

        val foundScopes = result.events.filterIsInstance<TokenTreeEmitter.Event.Start>().map { it.scope }.distinct()
        
        println("Found scopes: $foundScopes")

        // Verify comment
        assertTrue("Should have comment scope", foundScopes.contains("comment"))
        
        // Verify keyword 'for'
        assertTrue("Should have keyword scope", foundScopes.contains("keyword"))
        
        // Verify string
        assertTrue("Should have string scope", foundScopes.contains("string"))
        
        // Verify number
        assertTrue("Should have number scope", foundScopes.contains("number"))
    }
}
