package com.zhengdianfang.highlightr.languages

import com.zhengdianfang.highlightr.HighlightEngine
import com.zhengdianfang.highlightr.TokenTreeEmitter
import org.junit.Assert.assertTrue
import org.junit.Test

class TypeScriptLanguageTest {

    @Test
    fun testTypeScriptHighlighting() {
        val engine = HighlightEngine()
        engine.registerLanguage("typescript", TypeScriptLanguage.get())

        val code = """
            // A simple TypeScript example
            interface Shape {
                area(): number;
            }

            enum Color { Red, Green, Blue }

            class Circle implements Shape {
                constructor(private radius: number) {}
                area(): number {
                    return Math.PI * this.radius * this.radius;
                }
            }

            const msg: string = `Hello world`;
            const c = new Circle(5);
            console.log(c.area());
        """.trimIndent()

        val result = engine.highlight(code, "typescript")
        val foundScopes = result.events
            .filterIsInstance<TokenTreeEmitter.Event.Start>()
            .map { it.scope }
            .distinct()

        println("Found scopes: $foundScopes")

        assertTrue("Should have comment scope", foundScopes.contains("comment"))
        assertTrue("Should have keyword scope", foundScopes.contains("keyword"))
        assertTrue("Should have string scope", foundScopes.contains("string"))
        assertTrue("Should have number scope", foundScopes.contains("number"))
        assertTrue("Should have title scope", foundScopes.contains("title"))
        assertTrue("Should have function scope", foundScopes.contains("function"))
    }
}
