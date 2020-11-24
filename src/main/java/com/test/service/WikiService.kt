package com.test.service

import com.test.model.WikiElement
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

/**
 * Wiki service.
 */
object WikiService {

    /**
     * 概要element取得
     * @param keyword キーワード
     */
    fun overviewElement(keyword: String): WikiElement {
        val document = request(keyword)
        val elements = document.select("div.mw-parser-output p")
        return WikiElement(elements.first()) // 概要は最初のpタグ取得
    }

    /**
     * wikiへのリクエスト
     * @param keyword キーワード
     */
    fun request(keyword: String): Document {
        Thread.sleep(1000) // 安全に１秒待ってから
        return Jsoup.connect("https://ja.wikipedia.org/wiki/${keyword}").get()
    }
}
