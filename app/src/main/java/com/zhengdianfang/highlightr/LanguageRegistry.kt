package com.zhengdianfang.highlightr

import com.zhengdianfang.highlightr.model.LanguageDefinition

object LanguageRegistry {

    private val languages = mutableMapOf<String, LanguageDefinition>()

    fun register(language: LanguageDefinition) {
        languages[language.id] = language
    }

    fun get(languageId: String): LanguageDefinition? = languages[languageId]
}