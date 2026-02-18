package com.zhengdianfang.highlightr.languages

import com.zhengdianfang.highlightr.HighlightEngine
import com.zhengdianfang.highlightr.TokenTreeEmitter
import org.junit.Test
import org.junit.Assert.*

class XmlLanguageTest {

    @Test
    fun testXmlHighlighting() {
        val engine = HighlightEngine()
        engine.registerLanguage("xml", XmlLanguage.get())
        
        val code = """<?xml version="1.0" encoding="UTF-8"?>"""
        
        val result = engine.highlight(code, "xml")
        
        assertTrue(result.events.isNotEmpty())
        
        val events = result.events
        
        fun hasScope(scope: String): Boolean {
            return events.any { it is TokenTreeEmitter.Event.Start && it.scope == scope }
        }
        
        assertTrue("Should have meta scope for processing instruction", hasScope("meta"))
        assertTrue("Should have literal scope for xml keywords", hasScope("literal"))
        assertTrue("Should have string scope for attributes", hasScope("string"))
    }
}
