package com.zhengdianfang.highlightr.languages

import com.zhengdianfang.highlightr.HighlightEngine
import com.zhengdianfang.highlightr.TokenTreeEmitter
import org.junit.Test
import org.junit.Assert.*

class CssLanguageTest {

    @Test
    fun testCssHighlighting() {
        val engine = HighlightEngine()
        engine.registerLanguage("css", CssLanguage.get())
        
        val code = """
            /* Comment */
            body {
                color: #333;
                background-color: white;
                font-size: 14px;
            }
            #header {
                width: 100%;
            }
            .class-name {
                display: flex;
            }
            @media (max-width: 600px) {
                .sidebar { display: none; }
            }
        """.trimIndent()
        
        val result = engine.highlight(code, "css")
        
        // Basic check to see if we got events
        assertTrue(result.events.isNotEmpty())
        
        // Check for specific tokens
        val events = result.events
        
        // Helper to find scope
        fun hasScope(scope: String): Boolean {
            return events.any { it is TokenTreeEmitter.Event.Start && it.scope == scope }
        }
        
        assertTrue("Should have comment scope", hasScope("comment"))
        assertTrue("Should have selector-id scope", hasScope("selector-id"))
        assertTrue("Should have selector-class scope", hasScope("selector-class"))
        assertTrue("Should have selector-tag scope", hasScope("selector-tag"))
        assertTrue("Should have attribute scope (properties)", hasScope("attribute"))
        assertTrue("Should have number scope (values)", hasScope("number"))
        assertTrue("Should have keyword scope (at-rules)", hasScope("keyword"))
    }
}
