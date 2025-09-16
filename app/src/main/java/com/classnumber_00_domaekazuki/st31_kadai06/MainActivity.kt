package com.classnumber_00_domaekazuki.st31_kadai06

import android.app.AlertDialog
import android.os.Bundle
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Toast
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TodoRecyclerAdapter
    private lateinit var databaseRepository: DatabaseRepository
    private lateinit var fabAdd: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initViews()
        setupDatabase()
        setupRecyclerView()
        setupFab()
        loadTodos()
    }

    /**
     * ビューの初期化
     */
    private fun initViews() {
        recyclerView = findViewById(R.id.recyclerView)
        fabAdd = findViewById(R.id.fabAdd)
    }

    /**
     * データベースの初期化
     */
    private fun setupDatabase() {
        databaseRepository = DatabaseRepository(this)
    }

    /**
     * RecyclerViewの設定
     */
    private fun setupRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        adapter = TodoRecyclerAdapter(this, ArrayList())
        recyclerView.adapter = adapter

        // アイテムクリック（編集）
        adapter.onItemClickListener = { selectedTodo ->
            showEditTodoDialog(selectedTodo)
        }

        // アイテム長押し（削除）
        adapter.onItemLongClickListener = { selectedTodo ->
            showDeleteConfirmDialog(selectedTodo)
        }

        // 完了状態切り替え
        adapter.onToggleCompleteListener = { selectedTodo ->
            toggleTodoCompletion(selectedTodo)
        }
    }

    /**
     * FloatingActionButtonの設定
     */
    private fun setupFab() {
        fabAdd.setOnClickListener {
            showAddTodoDialog()
        }
    }

    /**
     * ToDoリストを読み込み
     */
    private fun loadTodos() {
        val todoList = databaseRepository.getAllTodos()
        adapter.updateData(todoList)
    }

    /**
     * ToDoの完了状態を切り替え
     */
    private fun toggleTodoCompletion(todoData: TodoData) {
        if (databaseRepository.toggleTodoCompletion(todoData.id)) {
            loadTodos() // リストを再読み込み
            val message = if (!todoData.isCompleted) "完了しました！" else "未完了に戻しました"
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "更新に失敗しました", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * ToDo追加ダイアログを表示
     */
    private fun showAddTodoDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_todo, null)
        val titleEdit = dialogView.findViewById<EditText>(R.id.editTitle)
        val descriptionEdit = dialogView.findViewById<EditText>(R.id.editDescription)
        val priorityGroup = dialogView.findViewById<RadioGroup>(R.id.priorityGroup)

        AlertDialog.Builder(this)
            .setTitle("新しいタスクを追加")
            .setView(dialogView)
            .setPositiveButton("追加") { _, _ ->
                val title = titleEdit.text.toString().trim()
                val description = descriptionEdit.text.toString().trim()

                if (title.isEmpty()) {
                    Toast.makeText(this, "タイトルを入力してください", Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }

                val priority = when (priorityGroup.checkedRadioButtonId) {
                    R.id.priorityHigh -> 1
                    R.id.priorityMedium -> 2
                    R.id.priorityLow -> 3
                    else -> 2
                }

                if (databaseRepository.addTodo(title, description, priority)) {
                    loadTodos()
                    Toast.makeText(this, "タスクを追加しました", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "追加に失敗しました", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("キャンセル", null)
            .show()
    }

    /**
     * ToDo編集ダイアログを表示
     */
    private fun showEditTodoDialog(todoData: TodoData) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_todo, null)
        val titleEdit = dialogView.findViewById<EditText>(R.id.editTitle)
        val descriptionEdit = dialogView.findViewById<EditText>(R.id.editDescription)
        val priorityGroup = dialogView.findViewById<RadioGroup>(R.id.priorityGroup)

        // 現在の値を設定
        titleEdit.setText(todoData.title)
        descriptionEdit.setText(todoData.description)

        when (todoData.priority) {
            1 -> priorityGroup.check(R.id.priorityHigh)
            2 -> priorityGroup.check(R.id.priorityMedium)
            3 -> priorityGroup.check(R.id.priorityLow)
        }

        AlertDialog.Builder(this)
            .setTitle("タスクを編集")
            .setView(dialogView)
            .setPositiveButton("更新") { _, _ ->
                val title = titleEdit.text.toString().trim()
                val description = descriptionEdit.text.toString().trim()

                if (title.isEmpty()) {
                    Toast.makeText(this, "タイトルを入力してください", Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }

                val priority = when (priorityGroup.checkedRadioButtonId) {
                    R.id.priorityHigh -> 1
                    R.id.priorityMedium -> 2
                    R.id.priorityLow -> 3
                    else -> 2
                }

                if (databaseRepository.updateTodo(todoData.id, title, description, todoData.isCompleted, priority)) {
                    loadTodos()
                    Toast.makeText(this, "タスクを更新しました", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "更新に失敗しました", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("キャンセル", null)
            .show()
    }

    /**
     * 削除確認ダイアログを表示
     */
    private fun showDeleteConfirmDialog(todoData: TodoData) {
        AlertDialog.Builder(this)
            .setTitle("削除確認")
            .setMessage("「${todoData.title}」を削除しますか？")
            .setPositiveButton("削除") { _, _ ->
                if (databaseRepository.deleteTodo(todoData.id)) {
                    loadTodos()
                    Toast.makeText(this, "タスクを削除しました", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "削除に失敗しました", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("キャンセル", null)
            .show()
    }

    override fun onDestroy() {
        super.onDestroy()
        // データベース接続を閉じる（オプション）
    }
}