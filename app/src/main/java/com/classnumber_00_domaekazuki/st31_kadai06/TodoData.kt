package com.classnumber_00_domaekazuki.st31_kadai06

data class TodoData(
    val id: Long,
    val title: String,
    val description: String,
    val isCompleted: Boolean,
    val priority: Int // 1:高 2:中 3:低
) {
    // 優先度を文字列で取得
    fun getPriorityText(): String {
        return when (priority) {
            1 -> "高"
            2 -> "中"
            3 -> "低"
            else -> "中"
        }
    }

    // 優先度の色を取得（リソースIDを返す）
    fun getPriorityColor(): Int {
        return when (priority) {
            1 -> android.R.color.holo_red_light
            2 -> android.R.color.holo_orange_light
            3 -> android.R.color.holo_green_light
            else -> android.R.color.holo_orange_light
        }
    }
}