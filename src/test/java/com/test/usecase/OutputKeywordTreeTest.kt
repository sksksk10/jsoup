package com.test.usecase

import com.test.model.Keyword
import org.junit.jupiter.api.Test

class OutputKeywordTreeTest {

    @Test
    fun `ツリー出力できること`() {
        val root = Keyword(value = "root", level = 0)
        val level1_1 = Keyword(value = "level1_1", level = 1)
        val level1_2 = Keyword(value = "level1_2", level = 1)
        val level1 = listOf(level1_1, level1_2)
        val level2_1 = Keyword(value = "level2_1", level = 2)
        val level2_2 = Keyword(value = "level2_2", level = 2)
        val level2_3 = Keyword(value = "level2_3", level = 2)
        val level2 = listOf(level2_1, level2_2, level2_3)
        val level3_1 = Keyword(value = "level3_1", level = 3)
        val level3_2 = Keyword(value = "level3_2", level = 3)
        val level3 = listOf(level3_1, level3_2)
        val level4_1 = Keyword(value = "level4_1", level = 4)
        val level4 = listOf(level4_1)

        level3_2.addChildren(level4)
        level2_2.addChildren(level3)
        level1_1.addChildren(level2)
        root.addChildren(level1)

        OutputKeywordTree(root).invoke()
    }
}
