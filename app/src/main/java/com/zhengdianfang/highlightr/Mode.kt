package com.zhengdianfang.highlightr

open class Mode(
    val className: String? = null,
    val begin: String? = null,
    val end: String? = null,
    val beginKeywords: String? = null,
    val endsWithParent: Boolean = false,
    val endsParent: Boolean = false,
    val lexemes: String = "\\w+",
    val keywords: Map<String, String>? = null,
    val illegal: String? = null,
    val contains: List<Mode> = emptyList(),
    val subLanguage: String? = null,
    val variants: List<Mode> = emptyList(),
    
    // Compilation artifacts
    var compiledBegin: Regex? = null,
    var compiledEnd: Regex? = null,
    var compiledIllegal: Regex? = null,
    var terminators: Regex? = null,
    var parent: Mode? = null
)
