package com.test.model

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class KeywordTest {

    @Test
    fun `EMPTYであること`() {
        val actual = Keyword.EMPTY
        assertTrue(actual.isEmpty())
    }

    @Test
    fun `子の操作ができること`() {
        val expected1 = Keyword(
            value = "テスト1",
            level = 2
        )
        val expected2 = Keyword(
            value = "テスト2",
            level = 2
        )

        val testData = listOf(expected1, expected2)

        val actual = Keyword(
            value = "main",
            level = 1
        )
        actual.addChildren(testData)

        assertEquals(2, actual.size())
        assertTrue(actual.hasChildren())
        assertEquals(actual.get(0), expected1)
        assertEquals(actual.get(1), expected2)
    }

    @Nested
    inner class Output {
        @Test
        fun `階層出力できること_探索済み`() {
            val actual = Keyword(
                value = "テスト",
                level = 3
            )
            actual.setChecked()
            assertEquals("      - テスト", actual.levelFormat())
        }

        @Test
        fun `階層出力できること_未探索で重複あり_取得したものに重複判定しているので`() {
            val actual = Keyword(
                value = "テスト",
                level = 3
            )
            actual.setDuplicated()
            assertEquals("      - テスト@", actual.levelFormat())
        }

        @Test
        fun `階層出力できること_未探索`() {
            val actual = Keyword(
                value = "テスト",
                level = 3
            )
            assertEquals("      - テスト$", actual.levelFormat())
        }

        @Test
        fun `階層出力できること_探索済みで重複あり_起こり得ないが`() {
            val actual = Keyword(
                value = "テスト",
                level = 3
            )
            actual.setChecked()
            actual.setDuplicated()
            assertEquals("      - テスト@", actual.levelFormat())
        }
    }

    @Nested
    inner class Duplicated {
        @Test
        fun `重複ではない`() {
            val actual = Keyword(
                value = "テスト",
                level = 3
            )
            assertTrue(actual.isNotDuplicated())
        }

        @Test
        fun `重複である`() {
            val actual = Keyword(
                value = "テスト",
                level = 3
            )
            actual.setDuplicated()
            assertFalse(actual.isNotDuplicated())
        }
    }
}
