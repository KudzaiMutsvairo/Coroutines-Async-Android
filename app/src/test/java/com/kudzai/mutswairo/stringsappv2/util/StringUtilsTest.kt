package com.kudzai.mutswairo.stringsappv2.util

import com.kudzai.mutswairo.stringsappv2.util.StringUtils.escapeSpecialCharacter
import com.kudzai.mutswairo.stringsappv2.util.StringUtils.replaceSpecialCharacters
import org.junit.Assert.assertEquals
import org.junit.Test

class StringUtilsTest {

    @Test
    fun `replaceSpecialCharacters() replaces new line characters`() {
        val testString = "\n"
        assertEquals("\\n", testString.replaceSpecialCharacters())
    }

    @Test
    fun `replaceSpecialCharacters() replaces tab characters`() {
        val testString = "\t"
        assertEquals("\\t", testString.replaceSpecialCharacters())
    }

    @Test
    fun `replaceSpecialCharacters() replaces space characters`() {
        val testString = "hello world"
        assertEquals("hello world", testString.replaceSpecialCharacters())
    }

    @Test
    fun `replaceSpecialCharacters() does not replace other characters`() {
        assertEquals("hello@world", "hello@world".replaceSpecialCharacters())
    }

    @Test
    fun `escapeSpecialCharacter() replaces new line characters`() {
        assertEquals("\\n", '\n'.escapeSpecialCharacter())
    }

    @Test
    fun `escapeSpecialCharacter() replaces tab characters`() {
        assertEquals("\\t", '\t'.escapeSpecialCharacter())
    }

    @Test
    fun `escapeSpecialCharacter() replaces space characters`() {
        assertEquals("\\s", ' '.escapeSpecialCharacter())
    }

    @Test
    fun `escapeSpecialCharacter() does not replace other characters`() {
        assertEquals("@", '@'.escapeSpecialCharacter())
    }
}
