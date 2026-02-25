package com.zhengdianfang.highlightr.languages

import com.zhengdianfang.highlightr.Mode

object PhpLanguage {
    fun get(): Mode {
        val KEYWORDS = mapOf(
            "keyword" to "abstract and array as break case catch class clone const continue declare default do else elseif enddeclare endfor endforeach endif endswitch endwhile extends final finally for foreach function global goto if implements interface instanceof namespace new or private protected public static switch throw trait try use var while xor yield",
            "built_in" to "__CLASS__ __DIR__ __FILE__ __FUNCTION__ __LINE__ __METHOD__ __NAMESPACE__ __TRAIT__ die echo empty exit eval include include_once isset list require require_once return print unset",
            "literal" to "true false null"
        )
        
        val KEYWORD_MODE = Mode(
            className = "keyword",
            begin = "\\b(${KEYWORDS["keyword"]!!.replace(" ", "|")})\\b",
            end = ""
        )
        
        val BUILT_IN_MODE = Mode(
            className = "built_in",
            begin = "\\b(${KEYWORDS["built_in"]!!.replace(" ", "|")})\\b",
            end = ""
        )
        
        val LITERAL_MODE = Mode(
            className = "literal",
            begin = "\\b(${KEYWORDS["literal"]!!.replace(" ", "|")})\\b",
            end = ""
        )

        val PREPROCESSOR = Mode(
            className = "keyword",
            begin = "<\\?php|\\?>",
            end = ""
        )

        val VARIABLE = Mode(
            className = "variable",
            begin = "\\\$[a-zA-Z_\\x7f-\\xff][a-zA-Z0-9_\\x7f-\\xff]*",
            end = ""
        )
        
        val NAMESPACE_ID = Mode(
            className = "function",
            begin = "(?<=\\bnamespace\\s)[a-zA-Z_\\x7f-\\xff][a-zA-Z0-9_\\x7f-\\xff]*",
            end = ""
        )
        
        val USE_ID = Mode(
            className = "function",
            begin = "(?<=\\buse\\s)[a-zA-Z_\\x7f-\\xff][a-zA-Z0-9_\\x7f-\\xff]*",
            end = ""
        )
        
        val PATH_ID = Mode(
            className = "function",
            begin = "(?<=\\\\)[a-zA-Z_\\x7f-\\xff][a-zA-Z0-9_\\x7f-\\xff]*",
            end = ""
        )
        
        val INTERPOLATED_VARIABLE = Mode(
            className = "template-variable",
            begin = "\\\$[a-zA-Z_\\x7f-\\xff][a-zA-Z0-9_\\x7f-\\xff]*",
            end = ""
        )

        val STRING_DOUBLE = Mode(
            className = "string",
            begin = "\"", 
            end = "\"", 
            contains = listOf(
                Mode(begin = "\\\\."),
                INTERPOLATED_VARIABLE
            )
        )
        
        val STRING_SINGLE = Mode(
            className = "string",
            begin = "'", 
            end = "'", 
            contains = listOf(Mode(begin = "\\\\."))
        )

        val NUMBER = Mode(
            className = "number",
            begin = "\\b(0[xX][0-9a-fA-F]+|0[bB][01]+|\\d+(\\.\\d+)?)\\b",
            end = ""
        )

        val COMMENT_SINGLE = Mode(
            className = "comment",
            begin = "//|#",
            end = "\n"
        )

        val COMMENT_MULTI = Mode(
            className = "comment",
            begin = "/\\*",
            end = "\\*/"
        )
        
        val CLASS_DEFINITION = Mode(
            className = "title",
            begin = "(?<=\\bclass\\s)\\s*[a-zA-Z_\\x7f-\\xff][a-zA-Z0-9_\\x7f-\\xff]*",
            end = ""
        )
        
        val INTERFACE_DEFINITION = Mode(
            className = "title",
            begin = "(?<=\\binterface\\s)\\s*[a-zA-Z_\\x7f-\\xff][a-zA-Z0-9_\\x7f-\\xff]*",
            end = ""
        )

        val TRAIT_DEFINITION = Mode(
            className = "title",
            begin = "(?<=\\btrait\\s)\\s*[a-zA-Z_\\x7f-\\xff][a-zA-Z0-9_\\x7f-\\xff]*",
            end = ""
        )
        
        val EXTENDS_DEFINITION = Mode(
            className = "title",
            begin = "(?<=\\bextends\\s)\\s*[a-zA-Z_\\x7f-\\xff][a-zA-Z0-9_\\x7f-\\xff]*",
            end = ""
        )
        
        val IMPLEMENTS_DEFINITION = Mode(
            className = "title",
            begin = "(?<=\\bimplements\\s)\\s*[a-zA-Z_\\x7f-\\xff][a-zA-Z0-9_\\x7f-\\xff]*",
            end = ""
        )

        val FUNCTION_DEFINITION = Mode(
            className = "function",
            begin = "(?<=\\bfunction\\s)\\s*[a-zA-Z_\\x7f-\\xff][a-zA-Z0-9_\\x7f-\\xff]*",
            end = ""
        )
        
        val NEW_OBJECT = Mode(
            className = "title",
            begin = "(?<=\\bnew\\s)\\s*[a-zA-Z_\\x7f-\\xff][a-zA-Z0-9_\\x7f-\\xff]*",
            end = ""
        )
        
        val KEYWORDS_PATTERN = "(?:if|else|elseif|for|foreach|while|switch|catch|array|list|empty|eval|exit|isset|unset|die|include|include_once|require|require_once|return|print)"
        val FUNCTION_CALL = Mode(
            className = "function",
            begin = "\\b(?!$KEYWORDS_PATTERN\\b)[a-zA-Z_\\x7f-\\xff][a-zA-Z0-9_\\x7f-\\xff]*(?=\\s*\\()",
            end = ""
        )

        val CONSTANT = Mode(
            className = "constant",
            begin = "\\b[A-Z][A-Z0-9_]*\\b",
            end = ""
        )
        
        val STATIC_CLASS = Mode(
            className = "title",
            begin = "[a-zA-Z_\\x7f-\\xff][a-zA-Z0-9_\\x7f-\\xff]*(?=\\:\\:)",
            end = ""
        )

        return Mode(
            className = "php",
            keywords = KEYWORDS,
            contains = listOf(
                COMMENT_SINGLE,
                COMMENT_MULTI,
                PREPROCESSOR,
                STRING_DOUBLE,
                STRING_SINGLE,
                VARIABLE,
                CLASS_DEFINITION,
                INTERFACE_DEFINITION,
                TRAIT_DEFINITION,
                EXTENDS_DEFINITION,
                IMPLEMENTS_DEFINITION,
                NAMESPACE_ID,
                USE_ID,
                PATH_ID,
                FUNCTION_DEFINITION,
                NEW_OBJECT,
                STATIC_CLASS,
                FUNCTION_CALL,
                CONSTANT,
                KEYWORD_MODE,
                BUILT_IN_MODE,
                LITERAL_MODE,
                NUMBER
            )
        )
    }
}
