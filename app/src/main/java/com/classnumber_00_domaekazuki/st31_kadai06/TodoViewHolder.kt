package com.classnumber_00_domaekazuki.st31_kadai06

import android.graphics.Paint
import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class TodoViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    private val titleTextView = itemView.findViewById<TextView>(R.id.todoTitle)
    private val descriptionTextView = itemView.findViewById<TextView>(R.id.todoDescription)
    private val priorityTextView = itemView.findViewById<TextView>(R.id.todoPriority)
    private val completedCheckBox = itemView.findViewById<CheckBox>(R.id.todoCompleted)

    /**
     * データを画面に表示するメソッド
     */
    fun bind(todoData: TodoData) {
        titleTextView.text = todoData.title
        descriptionTextView.text = todoData.description
        priorityTextView.text = "優先度: ${todoData.getPriorityText()}"
        completedCheckBox.isChecked = todoData.isCompleted

        // 優先度による色分け
        val priorityColor = ContextCompat.getColor(itemView.context, todoData.getPriorityColor())
        priorityTextView.setTextColor(priorityColor)

        // 完了済みタスクのスタイル設定
        if (todoData.isCompleted) {
            // 取り消し線を追加
            titleTextView.paintFlags = titleTextView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            descriptionTextView.paintFlags = descriptionTextView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

            // 薄いグレー色に変更
            val grayColor = ContextCompat.getColor(itemView.context, android.R.color.darker_gray)
            titleTextView.setTextColor(grayColor)
            descriptionTextView.setTextColor(grayColor)

            // 背景を薄くする
            itemView.alpha = 0.6f
        } else {
            // 取り消し線を削除
            titleTextView.paintFlags = titleTextView.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            descriptionTextView.paintFlags = descriptionTextView.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()

            // 通常の色に戻す
            val defaultColor = ContextCompat.getColor(itemView.context, android.R.color.primary_text_light)
            titleTextView.setTextColor(defaultColor)
            descriptionTextView.setTextColor(defaultColor)

            // 透明度を戻す
            itemView.alpha = 1.0f
        }
    }

    /**
     * クリックリスナーを設定するメソッド
     */
    fun setClickListener(todoData: TodoData, clickListener: (TodoData) -> Unit) {
        itemView.setOnClickListener {
            clickListener(todoData)
        }
    }

    /**
     * 長押しリスナーを設定するメソッド
     */
    fun setLongClickListener(todoData: TodoData, longClickListener: (TodoData) -> Unit) {
        itemView.setOnLongClickListener {
            longClickListener(todoData)
            true
        }
    }

    /**
     * 完了切り替えリスナーを設定するメソッド
     */
    fun setToggleCompleteListener(todoData: TodoData, toggleListener: (TodoData) -> Unit) {
        completedCheckBox.setOnCheckedChangeListener { _, _ ->
            toggleListener(todoData)
        }
    }

    /**
     * クリックリスナーを削除するメソッド
     */
    fun clearClickListeners() {
        itemView.setOnClickListener(null)
        itemView.setOnLongClickListener(null)
        completedCheckBox.setOnCheckedChangeListener(null)
    }
}