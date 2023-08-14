package com.kudzai.mutswairo.stringsappv2.presentation.viewStrings.model

/**
 * data class that holds the state of the screen
 */
data class StringsScreenStateData(
    val tenthChar: String = "",
    val everyTenthChar: List<String> = emptyList(),
    val uniqueWordsAndCount: Map<String, Int> = emptyMap(),
)
