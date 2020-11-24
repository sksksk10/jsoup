package com.test.model

import org.jsoup.nodes.Element
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class WikiElementTest {

    @Nested
    inner class Keywords {
        @Test
        fun `キーワードスクレイピングできること`() {
            val testData = Element("p")
                .append("<a href=\"/aaa\" title=\"test\">test</a>") // 除外対象
                .append("<a href=\"/wiki/test\" title=\"test\">test</a>")
                .append("<a href=\"/wiki/英語\" title=\"英語\">英語</a>") // 除外対象

            val expected = Keyword(
                value = "test",
                level = 1
            )

            val actual = WikiElement(testData).scrapingKeywords(1)
            assertEquals(1, actual.size)
            assertEquals(expected, actual[0])
        }

        @Test
        fun `キーワードスクレイピングは空_対象外のみ`() {
            val testData = Element("p")
                .append("<a href=\"/aaa\" title=\"test\">test</a>") // 除外対象
                .append("<a href=\"/wiki/英語\" title=\"英語\">英語</a>") // 除外対象

            val actual = WikiElement(testData).scrapingKeywords(1)
            assertTrue(actual.isEmpty())
        }

        @Test
        fun `キーワードスクレイピングは空_aタグなし`() {
            val testData = Element("p")
                .append("<span>test</span>")

            val actual = WikiElement(testData).scrapingKeywords(1)
            assertTrue(actual.isEmpty())
        }

        @Test
        fun `キーワードスクレイピングは空_elementなし`() {
            val testData: Element? = null

            val actual = WikiElement(testData).scrapingKeywords(1)
            assertTrue(actual.isEmpty())
        }
    }
}
