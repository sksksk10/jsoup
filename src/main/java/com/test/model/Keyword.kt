package com.test.model

/**
 * キーワード
 */
data class Keyword(
    val value: String = "", // ワード
    private val level: Int = 0 // 階層
) {
    private val children = ArrayList<Keyword>() // 子階層キーワードリスト
    private var isChecked = false // 探索済み
    private var isDuplicated = false // 重複

    // 子の操作系
    fun addChildren(keywords: List<Keyword>) = children.addAll(keywords)
    fun get(index: Int) = children[index]
    fun size() = children.size
    fun hasChildren() = children.isNotEmpty()

    // マーク操作系
    fun setChecked() {
        isChecked = true
    }
    fun setDuplicated() {
        isDuplicated = true
    }
    fun isNotDuplicated() = !isDuplicated
    private fun stoppedMark() = if (isChecked || isDuplicated) "" else STOPPED
    private fun duplicatedMark() = if (isDuplicated) DUPLICATED else ""

    fun isEmpty() = this == EMPTY

    // 階層出力ォーマット
    fun levelFormat() = "${"  ".repeat(level)}- $value${stoppedMark()}${duplicatedMark()}"

    companion object {
        val EMPTY = Keyword()
        private const val STOPPED = "$"
        private const val DUPLICATED = "@"
    }
}
