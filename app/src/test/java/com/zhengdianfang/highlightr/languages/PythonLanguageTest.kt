package com.zhengdianfang.highlightr.languages

import com.zhengdianfang.highlightr.HighlightEngine
import com.zhengdianfang.highlightr.TokenTreeEmitter
import org.junit.Test
import org.junit.Assert.*

class PythonLanguageTest {

    @Test
    fun testPythonHighlighting() {
        val engine = HighlightEngine()
        engine.registerLanguage("python", PythonLanguage.get())
        
        val code = """
            # This is a comment
            import os
            
            @decorator
            class MyClass(object):
                def __init__(self, value):
                    self.value = value
                    
                def get_value(self):
                    return self.value
                    
            def main():
                x = 10
                y = 3.14
                s = "Hello World"
                print(x + y)
                
            if __name__ == "__main__":
                main()
        """.trimIndent()
        
        val result = engine.highlight(code, "python")
        
        val foundScopes = result.events.filterIsInstance<TokenTreeEmitter.Event.Start>().map { it.scope }.distinct()
        
        assertTrue("Should have comment scope. Found: $foundScopes", foundScopes.contains("comment"))
        assertTrue("Should have keyword scope. Found: $foundScopes", foundScopes.contains("keyword"))
        // class and function modes use keyword scope for the keyword itself
        // but we also check for specific scopes if they are used
        
        // In my implementation:
        // class -> keyword
        // def -> keyword
        // function name -> title
        // params -> params
        
        // So we should check for title and params
        assertTrue("Should have title scope. Found: $foundScopes", foundScopes.contains("title"))
        assertTrue("Should have function scope. Found: $foundScopes", foundScopes.contains("function"))
        assertTrue("Should have params scope. Found: $foundScopes", foundScopes.contains("params"))
        assertTrue("Should have number scope. Found: $foundScopes", foundScopes.contains("number"))
        assertTrue("Should have string scope. Found: $foundScopes", foundScopes.contains("string"))
        assertTrue("Should have meta scope. Found: $foundScopes", foundScopes.contains("meta"))
        assertTrue("Should have built_in scope. Found: $foundScopes", foundScopes.contains("built_in"))
    }
}
