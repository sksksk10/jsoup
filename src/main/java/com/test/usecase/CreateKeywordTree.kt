package com.test.usecase

import com.test.model.Keyword
import com.test.service.WikiService

/**
 * キーワードツリー作成
 */
class CreateKeywordTree(
    private val keywordValue: String = "" // 入力キーワード
) {
    private val searchedKeyword = HashSet<String>() // 探索済みキーワード

    /**
     * 実行
     */
    fun invoke(): Keyword {
        return try {
            val root = Keyword(keywordValue)
            search(listOf(root), 0, 0)
            root
        } catch (ex: Exception) {
            ex.printStackTrace()
            println("Tree作成に失敗しました")
            Keyword.EMPTY
        }
    }

    /**
     * 探索する
     * @param keywords キーワードリスト
     * @param times 探索回数
     * @param level 階層
     */
    private tailrec fun search(keywords: List<Keyword>, times: Int, level: Int): List<Keyword> {
        if (times > MAX_SEARCH_TIMES) return emptyList()
        val nextLevel = level + 1
        var count = times
        val results = keywords.filter(Keyword::isNotDuplicated).map { keyword -> // 同階層が優先なので回す
            if (++count > MAX_SEARCH_TIMES) return emptyList()
            val overviewElement = WikiService.overviewElement(keyword.value) // wikiからキーワード取得
            val scrapedKeywords = overviewElement.scrapingKeywords(nextLevel) // スクレイピング
            keyword.setChecked() // 探索済み忘れずに
            scrapedKeywords.map { scrapedKeyword ->
                if (searchedKeyword.contains(scrapedKeyword.value)) scrapedKeyword.setDuplicated() // 重複あり
                searchedKeyword.add(scrapedKeyword.value) // 同階層に重複出てくるかもしれないので随時登録
            }
            keyword.addChildren(scrapedKeywords) // 子としてぶら下げる
            scrapedKeywords
        }.flatten()
        return if (results.isEmpty()) emptyList() // 次ないなら抜ける
        else search(results, count, nextLevel) // 次の階層へ再帰
    }

    companion object {
        private const val MAX_SEARCH_TIMES = 20 // 最大探索回数
    }
}
