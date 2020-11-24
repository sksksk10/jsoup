package com.test.usecase

import com.test.model.Keyword
import com.test.model.WikiElement
import com.test.service.WikiService
import io.mockk.every
import io.mockk.mockkObject
import io.mockk.verify
import org.jsoup.HttpStatusException
import org.jsoup.nodes.Element
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class CreateKeywordTreeTest {

    @Test
    fun `探索できること`() {

        // WikiServiceをmock化
        val testData = listOf(
            WikiElement(Element("p").append("<a href=\"/wiki/test1\" title=\"test1\">test</a>")),
            WikiElement(
                Element("p")
                    .append("<a href=\"/wiki/test1\" title=\"test1\">test</a>")
                    .append("<a href=\"/wiki/test4\" title=\"test4\">test</a>")
            )
        )
        mockkObject(WikiService)
        every { WikiService.overviewElement(allAny()) } returnsMany testData

        val expected = Keyword(value = "test", level = 0)
        val expected1 = Keyword(value = "test1", level = 1)
        val expected2 = Keyword(value = "test1", level = 2)
        expected2.setDuplicated()
        val expected3 = Keyword(value = "test4", level = 2)
        expected1.addChildren(listOf(expected2, expected3))
        expected.addChildren(listOf(expected1))

        val actual = CreateKeywordTree("test").invoke()

        assertEquals(expected, actual)
        assertEquals(1, actual.size())
        val actualChildKeyword = actual.get(0)
        assertEquals(expected1, actualChildKeyword)
        assertEquals(2, actualChildKeyword.size())
        assertEquals(expected2, actualChildKeyword.get(0))
        assertFalse(actualChildKeyword.get(0).isNotDuplicated())
        assertEquals(expected3, actualChildKeyword.get(1))
    }

    @Test
    fun `探索できること_上限回数`() {

        // WikiServiceをmock化
        val testData = listOf(
            WikiElement(
                Element("p")
                    .append("<a href=\"/wiki/test1\" title=\"test1\">test</a>")
                    .append("<a href=\"/wiki/test2\" title=\"test2\">test</a>")
                    .append("<a href=\"/wiki/test3\" title=\"test3\">test</a>")
                    .append("<a href=\"/wiki/test4\" title=\"test4\">test</a>")
                    .append("<a href=\"/wiki/test5\" title=\"test5\">test</a>")
                    .append("<a href=\"/wiki/test6\" title=\"test6\">test</a>")
                    .append("<a href=\"/wiki/test7\" title=\"test7\">test</a>")
                    .append("<a href=\"/wiki/test8\" title=\"test8\">test</a>")
                    .append("<a href=\"/wiki/test9\" title=\"test9\">test</a>")
                    .append("<a href=\"/wiki/test10\" title=\"test10\">test</a>")
                    .append("<a href=\"/wiki/test11\" title=\"test11\">test</a>")
                    .append("<a href=\"/wiki/test12\" title=\"test12\">test</a>")
                    .append("<a href=\"/wiki/test13\" title=\"test13\">test</a>")
                    .append("<a href=\"/wiki/test14\" title=\"test14\">test</a>")
                    .append("<a href=\"/wiki/test15\" title=\"test15\">test</a>")
                    .append("<a href=\"/wiki/test16\" title=\"test16\">test</a>")
                    .append("<a href=\"/wiki/test17\" title=\"test17\">test</a>")
                    .append("<a href=\"/wiki/test18\" title=\"test18\">test</a>")
                    .append("<a href=\"/wiki/test19\" title=\"test19\">test</a>")
                    .append("<a href=\"/wiki/test20\" title=\"test20\">test</a>")
            )
        )
        mockkObject(WikiService)
        every { WikiService.overviewElement(allAny()) } returnsMany testData

        val actual = CreateKeywordTree("test").invoke()
        assertEquals(20, actual.size())
        verify(exactly = 20) { WikiService.overviewElement(allAny()) }
        assertEquals("  - test20$", actual.get(actual.size()-1).levelFormat())
    }

    @Test
    fun `例外発生は空であること`() {

        // WikiServiceをmock化
        mockkObject(WikiService)
        every { WikiService.request("test") } throws HttpStatusException("test", 400, "test")

        val actual = CreateKeywordTree("test").invoke()
        assertEquals(Keyword.EMPTY, actual)
    }
}
