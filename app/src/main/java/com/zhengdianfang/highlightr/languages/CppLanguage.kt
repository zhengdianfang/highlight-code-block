package com.zhengdianfang.highlightr.languages

import com.zhengdianfang.highlightr.Mode

object CppLanguage {
    fun get(): Mode {
        val KEYWORDS = mapOf(
            "keyword" to "alignas alignof and and_eq asm atomic_cancel atomic_commit atomic_noexcept auto bitand bitor break case catch class compl concept constexpr const_cast continue co_await co_return co_yield decltype default delete do dynamic_cast else enum explicit export extern for friend goto if import inline module mutable namespace new noexcept not not_eq operator or or_eq private protected public register reinterpret_cast requires return sizeof static static_assert static_cast struct switch synchronized template this thread_local throw try typedef typeid typename union using virtual volatile while xor xor_eq",
            "built_in" to "int long short float double char void bool signed unsigned wchar_t char16_t char32_t const",
            "literal" to "true false nullptr NULL"
        )

        val STRING = Mode(
            className = "string",
            begin = "\"", 
            end = "\"",
            contains = listOf(
                Mode(className = "variable", begin = "\\\\[\\\\btnfr\"']")
            )
        )
        
        val PREPROCESSOR = Mode(
            className = "meta",
            begin = "#",
            end = "\n",
            keywords = mapOf(
                "keyword" to "include define undef if ifdef ifndef else elif endif line error pragma"
            ),
            contains = listOf(
                 Mode(className = "string", begin = "<", end = ">")
            )
        )

        val NUMBER = Mode(
            className = "number",
            begin = "\\b\\d+(\\.\\d+)?f?\\b",
            end = ""
        )
        
        val FUNCTION_DEFINITION = Mode(
            className = "function",
            begin = "\\b(?!if\\b|for\\b|while\\b|switch\\b|catch\\b|try\\b|do\\b|return\\b)\\w+(?=\\s*\\([^)]*\\)\\s*(const\\s*)?(\\{|:))",
            end = ""
        )

        val FUNCTION_CALL = Mode(
            className = "function-call",
            begin = "\\b(?!if\\b|for\\b|while\\b|switch\\b|catch\\b|try\\b|do\\b|return\\b)\\w+(?=\\s*\\()",
            end = ""
        )

        // Split class name regex to avoid variable-length lookbehind which can cause crashes on some Android versions
        val CLASS_NAME_CLASS = Mode(
            className = "title",
            begin = "(?<=\\bclass\\s)\\w+",
            end = ""
        )
        
        val CLASS_NAME_STRUCT = Mode(
            className = "title",
            begin = "(?<=\\bstruct\\s)\\w+",
            end = ""
        )
        
        val CLASS_NAME_ENUM = Mode(
            className = "title",
            begin = "(?<=\\benum\\s)\\w+",
            end = ""
        )

        return Mode(
            className = "cpp",
            keywords = KEYWORDS,
            contains = listOf(
                Mode(className = "comment", begin = "//", end = "\n"),
                Mode(className = "comment", begin = "/\\*", end = "\\*/"),
                PREPROCESSOR,
                STRING,
                NUMBER,
                FUNCTION_DEFINITION,
                FUNCTION_CALL,
                CLASS_NAME_CLASS,
                CLASS_NAME_STRUCT,
                CLASS_NAME_ENUM
            )
        )
    }
}
