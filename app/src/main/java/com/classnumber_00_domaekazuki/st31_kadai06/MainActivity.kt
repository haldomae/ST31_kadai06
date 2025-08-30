package com.classnumber_00_domaekazuki.st31_kadai06

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
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
        val listView: ListView = findViewById(R.id.listView)

        // 表示するデータ（果物のリスト）
        val fruits = arrayOf(
            "りんご",
            "バナナ",
            "オレンジ",
            "いちご",
            "ぶどう",
            "パイナップル",
            "メロン",
            "すいか",
            "桃",
            "柿"
        )

        // ArrayAdapterを作成
        val adapter = ArrayAdapter(
            this,                                    // コンテキスト
            android.R.layout.simple_list_item_1,     // 標準のリスト項目レイアウト
            fruits                                   // データ
        )

        // ListViewにAdapterを設定
        listView.adapter = adapter

        // リスト項目がクリックされた時の処理
        listView.setOnItemClickListener { parent, view, position, id ->
            val selectedFruit = fruits[position]
            Toast.makeText(
                this,
                "選択された果物: $selectedFruit",
                Toast.LENGTH_SHORT
            ).show()
        }

    }


}