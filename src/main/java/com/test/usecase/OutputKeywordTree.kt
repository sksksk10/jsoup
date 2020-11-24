package com.test.usecase

import com.test.model.Keyword

/**
 * キーワードツリー出力
 */
class OutputKeywordTree(
    private val root: Keyword // ツリーroot
) {
    /**
     * 実行
     */
    fun invoke() {
        if (root.isEmpty()) return
        println("--- 結果 -----------")
        println(root.levelFormat())
        if (root.hasChildren()) output(root)
    }

    /**
     * 出力する
     * @param keyword キーワード
     */
    private fun output(keyword: Keyword) {
        for (index in 0 until keyword.size()) {
            val child = keyword.get(index)
            println(child.levelFormat())
            if (child.hasChildren()) {
                output(child)
            }
        }
    }
}
