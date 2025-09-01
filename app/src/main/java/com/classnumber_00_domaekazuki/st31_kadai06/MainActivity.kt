package com.classnumber_00_domaekazuki.st31_kadai06

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // ListViewを取得
        val list: ListView = findViewById(R.id.listview)

        // 表示するデータ
        val fruits = arrayOf(
            "りんご",
            "バナナ",
            "オレンジ",
            "いちご",
            "ぶどう",
            "パイナップル"
        )

        // Adapterを作成
        // Adapterはリストとデータを中継してくれるもの
        val adapter = ArrayAdapter(
            this, // コンテキスト
            android.R.layout.simple_list_item_1, // 項目のレイアウト
            fruits // データ
        )
        // Adapterを設定
        list.adapter = adapter
    }
}