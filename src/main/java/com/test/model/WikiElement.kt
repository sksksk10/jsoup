package com.test.model

import org.jsoup.nodes.Element

/**
 * Wiki element.
 */
data class WikiElement(
    private val element: Element?
) {
    /**
     * キーワードをスクレイピング
     * @param level 対象階層
     */
    fun scrapingKeywords(level: Int): List<Keyword> {
        return element?.select("a")?.let {
            it.filter { aTag ->
                aTag.attr("href").startsWith("/wiki") // href は wiki 内であること
                    .and(aTag.attr("title") != "英語") // "英語" は除外
            }.map { aTag ->
                Keyword(aTag.attr("title"), level)
            }
        } ?: emptyList()
    }
}
