package com.test.service

import com.test.model.WikiElement
import io.mockk.every
import io.mockk.mockkObject
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class WikiServiceTest {

    @Test
    fun `概要elementが取得できること`() {
        val div = Element("div").addClass("mw-parser-output")
        val expected = Element("p").appendText("first")
        val second = Element("p").appendText("second")
        val third = Element("p").appendText("third")
        div.appendChild(expected).appendChild(second).appendChild(third)

        val returnData = Document.createShell("")
        returnData.body().appendChild(div)

        // wikiへのリクエストをmock化
        mockkObject(WikiService)
        every { WikiService.request("test") } returns returnData

        val actual = WikiService.overviewElement("test")
        assertEquals(WikiElement(expected), actual)
    }
}
