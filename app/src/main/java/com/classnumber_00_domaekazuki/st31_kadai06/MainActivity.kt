package com.classnumber_00_domaekazuki.st31_kadai06

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.SimpleAdapter
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
        val list: ListView = findViewById(R.id.listview)

        // 表示するデータ
//        val fruits = arrayOf(
//            "りんご",
//            "バナナ",
//            "オレンジ",
//            "いちご",
//            "ぶどう",
//            "パイナップル"
//        )

        // 画像付きデータ
        val monsterData = arrayListOf(
            mapOf("name" to "モンスター1",
                "image" to R.drawable.img1,
                "from" to "HAL東京30F"),
            mapOf("name" to "モンスター2",
                "image" to R.drawable.img2,
                "from" to "HAL東京29F"),
            mapOf("name" to "モンスター3",
                "image" to R.drawable.img3,
                "from" to "HAL東京28F"),
            mapOf("name" to "モンスター4",
                "image" to R.drawable.img4,
                "from" to "HAL東京27F"),
            mapOf("name" to "モンスター5",
                "image" to R.drawable.img5,
                "from" to "HAL東京26F"),
            mapOf("name" to "モンスター6",
                "image" to R.drawable.img6,
                "from" to "HAL東京25F"),
        )

        // Adapterを作成
        // Adapterはリストとデータを中継してくれるもの
//        val adapter = ArrayAdapter(
//            this, // コンテキスト
//            android.R.layout.simple_list_item_1, // 項目のレイアウト
//            fruits // データ
//        )
        val adapter = SimpleAdapter(
            this, // コンテキスト
            monsterData, // 表示したいデータ
            R.layout.list_item, // リストのレイアウト
            arrayOf("image","name", "from"),// 表示するデータのキー
            intArrayOf(R.id.itemImage, R.id.itemText, R.id.itemText2) // 対応するViewのID
        )
        // Adapterを設定
        list.adapter = adapter

        // 項目を押した時
        list.setOnItemClickListener{ parent, view, position, id ->
//            val currentData = fruits[position]
//            Toast.makeText(
//                this,
//                currentData,
//                Toast.LENGTH_SHORT
//            ).show()
        }
    }
}