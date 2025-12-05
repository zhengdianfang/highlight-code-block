package com.zhengdianfang.highlightr.theme

import android.graphics.Color
import com.zhengdianfang.highlightr.model.CodeTheme
import com.zhengdianfang.highlightr.model.TextStyle
import com.zhengdianfang.highlightr.model.TokenType

object DefaultThemes {

    val LightTheme = CodeTheme(
        defaultStyle = TextStyle(color = Color.BLACK),
        styleMap = mapOf(
            TokenType.KEYWORD to TextStyle(color = Color.parseColor("#A626A4"), bold = true),
            TokenType.STRING to TextStyle(color = Color.parseColor("#008000")),
            TokenType.COMMENT to TextStyle(color = Color.parseColor("#999999"), italic = true),
            TokenType.NUMBER to TextStyle(color = Color.parseColor("#986802")),
            TokenType.TYPE_NAME to TextStyle(color = Color.parseColor("#986802")),
            TokenType.ANNOTATION to TextStyle(color = Color.parseColor("#4078F2")),
            TokenType.IDENTIFIER to TextStyle(color = Color.BLACK),
            TokenType.PUNCTUATION to TextStyle(color = Color.DKGRAY),
            TokenType.LITERAL to TextStyle(color = Color.parseColor("#0084BB")),
            TokenType.FUNCTION_NAME to TextStyle(color = Color.parseColor("#4078F2")),
        )
    )
}