package com.classnumber_00_domaekazuki.st31_kadai06

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class TodoRecyclerAdapter(
    private val context: Context,
    private val todoList: ArrayList<TodoData>
): RecyclerView.Adapter<TodoViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    // クリックリスナーの定義
    var onItemClickListener: ((TodoData) -> Unit)? = null
    var onItemLongClickListener: ((TodoData) -> Unit)? = null
    var onToggleCompleteListener: ((TodoData) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val view = inflater.inflate(R.layout.todo_item, parent, false)
        return TodoViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val currentTodo = todoList[position]
        holder.bind(currentTodo)

        // クリックリスナーの設定
        onItemClickListener?.let { listener ->
            holder.setClickListener(currentTodo, listener)
        }

        // 長押しリスナーの設定
        onItemLongClickListener?.let { listener ->
            holder.setLongClickListener(currentTodo, listener)
        }

        // 完了切り替えリスナーの設定
        onToggleCompleteListener?.let { listener ->
            holder.setToggleCompleteListener(currentTodo, listener)
        }
    }

    override fun getItemCount(): Int {
        return todoList.size
    }

    /**
     * データ更新メソッド
     */
    fun updateData(newTodoList: ArrayList<TodoData>) {
        todoList.clear()
        todoList.addAll(newTodoList)
        notifyDataSetChanged()
    }

    /**
     * 単一アイテム追加メソッド
     */
    fun addItem(todoData: TodoData) {
        todoList.add(0, todoData) // 先頭に追加
        notifyItemInserted(0)
    }

    /**
     * アイテム削除メソッド
     */
    fun removeItem(position: Int) {
        if (position >= 0 && position < todoList.size) {
            todoList.removeAt(position)
            notifyItemRemoved(position)
        }
    }
}