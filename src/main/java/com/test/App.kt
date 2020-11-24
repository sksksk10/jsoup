package com.test

import com.test.usecase.CreateKeywordTree
import com.test.usecase.OutputKeywordTree
import java.util.*

/**
 * メイン
 */
object App {

    @JvmStatic
    fun main(args: Array<String>) {
        println("キーワードを入力してください")
        val input = Scanner(System.`in`).next()
        val root = CreateKeywordTree(input).invoke()
        OutputKeywordTree(root).invoke()
    }
}
