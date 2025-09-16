package com.classnumber_00_domaekazuki.st31_kadai06

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase

class DatabaseRepository(private val context: Context) {
    private val dbHelper: DatabaseHelper = DatabaseHelper(context)

    /**
     * 新しいToDoを追加するメソッド
     */
    fun addTodo(title: String, description: String, priority: Int = 2): Boolean {
        val db: SQLiteDatabase = dbHelper.writableDatabase
        var result = false

        try {
            val values = ContentValues().apply {
                put(DatabaseHelper.COLUMN_TITLE, title)
                put(DatabaseHelper.COLUMN_DESCRIPTION, description)
                put(DatabaseHelper.COLUMN_IS_COMPLETED, 0) // 未完了
                put(DatabaseHelper.COLUMN_PRIORITY, priority)
            }

            val newRowId = db.insert(DatabaseHelper.TABLE_TODOS, null, values)
            result = newRowId != -1L

        } catch (e: Exception) {
            e.printStackTrace()
            result = false
        } finally {
            db.close()
        }

        return result
    }

    /**
     * ToDoを更新するメソッド
     */
    fun updateTodo(id: Long, title: String, description: String, isCompleted: Boolean, priority: Int): Boolean {
        val db: SQLiteDatabase = dbHelper.writableDatabase
        var result = false

        try {
            val values = ContentValues().apply {
                put(DatabaseHelper.COLUMN_TITLE, title)
                put(DatabaseHelper.COLUMN_DESCRIPTION, description)
                put(DatabaseHelper.COLUMN_IS_COMPLETED, if (isCompleted) 1 else 0)
                put(DatabaseHelper.COLUMN_PRIORITY, priority)
            }

            val affectedRows = db.update(
                DatabaseHelper.TABLE_TODOS,
                values,
                "${DatabaseHelper.COLUMN_ID} = ?",
                arrayOf(id.toString())
            )

            result = affectedRows > 0

        } catch (e: Exception) {
            e.printStackTrace()
            result = false
        } finally {
            db.close()
        }

        return result
    }

    /**
     * ToDoの完了状態を切り替えるメソッド
     */
    fun toggleTodoCompletion(id: Long): Boolean {
        val db: SQLiteDatabase = dbHelper.writableDatabase
        var result = false

        try {
            // 現在の完了状態を取得
            val cursor = db.query(
                DatabaseHelper.TABLE_TODOS,
                arrayOf(DatabaseHelper.COLUMN_IS_COMPLETED),
                "${DatabaseHelper.COLUMN_ID} = ?",
                arrayOf(id.toString()),
                null, null, null
            )

            if (cursor.moveToFirst()) {
                val currentCompleted = cursor.getInt(0) == 1
                val newCompleted = !currentCompleted

                val values = ContentValues().apply {
                    put(DatabaseHelper.COLUMN_IS_COMPLETED, if (newCompleted) 1 else 0)
                }

                val affectedRows = db.update(
                    DatabaseHelper.TABLE_TODOS,
                    values,
                    "${DatabaseHelper.COLUMN_ID} = ?",
                    arrayOf(id.toString())
                )

                result = affectedRows > 0
            }

            cursor.close()

        } catch (e: Exception) {
            e.printStackTrace()
            result = false
        } finally {
            db.close()
        }

        return result
    }

    /**
     * ToDoを削除するメソッド
     */
    fun deleteTodo(id: Long): Boolean {
        val db: SQLiteDatabase = dbHelper.writableDatabase
        var result = false

        try {
            val affectedRows = db.delete(
                DatabaseHelper.TABLE_TODOS,
                "${DatabaseHelper.COLUMN_ID} = ?",
                arrayOf(id.toString())
            )

            result = affectedRows > 0

        } catch (e: Exception) {
            e.printStackTrace()
            result = false
        } finally {
            db.close()
        }

        return result
    }

    /**
     * 全てのToDoを取得するメソッド
     */
    @SuppressLint("Range")
    fun getAllTodos(): ArrayList<TodoData> {
        val todoList = ArrayList<TodoData>()
        val db: SQLiteDatabase = dbHelper.readableDatabase
        var cursor: Cursor? = null

        try {
            cursor = db.query(
                DatabaseHelper.TABLE_TODOS,
                null,
                null,
                null,
                null,
                null,
                "${DatabaseHelper.COLUMN_PRIORITY} ASC, ${DatabaseHelper.COLUMN_ID} DESC" // 優先度順、その後ID降順
            )

            if (cursor.moveToFirst()) {
                do {
                    val id = cursor.getLong(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID))
                    val title = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_TITLE))
                    val description = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_DESCRIPTION))
                    val isCompleted = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_IS_COMPLETED)) == 1
                    val priority = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_PRIORITY))

                    val todoData = TodoData(id, title, description, isCompleted, priority)
                    todoList.add(todoData)

                } while (cursor.moveToNext())
            }

        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            cursor?.close()
            db.close()
        }

        return todoList
    }

    /**
     * 指定されたIDのToDoを取得するメソッド
     */
    @SuppressLint("Range")
    fun getTodoById(id: Long): TodoData? {
        val db: SQLiteDatabase = dbHelper.readableDatabase
        var cursor: Cursor? = null
        var todoData: TodoData? = null

        try {
            cursor = db.query(
                DatabaseHelper.TABLE_TODOS,
                null,
                "${DatabaseHelper.COLUMN_ID} = ?",
                arrayOf(id.toString()),
                null, null, null
            )

            if (cursor.moveToFirst()) {
                val title = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_TITLE))
                val description = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_DESCRIPTION))
                val isCompleted = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_IS_COMPLETED)) == 1
                val priority = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_PRIORITY))

                todoData = TodoData(id, title, description, isCompleted, priority)
            }

        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            cursor?.close()
            db.close()
        }

        return todoData
    }
}