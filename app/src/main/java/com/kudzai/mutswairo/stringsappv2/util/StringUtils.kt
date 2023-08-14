package com.kudzai.mutswairo.stringsappv2.util

object StringUtils {

    private val specialCharactersMap = mapOf(
        '\n' to "\\n",
        '\t' to "\\t",
        ' ' to "\\s",
    )

    /**
     * Extension function to replace special characters with their escape characters
     */
    fun String.replaceSpecialCharacters(): String {
        return this.map { char ->
            specialCharactersMap[char] ?: char.toString()
        }.joinToString("")
    }

    /**
     * Extension function to replace special characters with their escape characters
     */
    fun Char.escapeSpecialCharacter(): String {
        return specialCharactersMap[this] ?: this.toString()
    }
}
