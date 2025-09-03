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

        // ListViewの取得
        val list: ListView = findViewById(R.id.listview)

        // 表示するデータ
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

        // Adapter
        // Adapterはリストとデータを仲介してくれるもの
//        val adapter = ArrayAdapter(
//            this, // コンテキスト(画面情報、どこの画面に表示するか)
//            android.R.layout.simple_list_item_1, // 項目のレイアウト
//            fruits // 表示するデータ
//        )
        val adapter = SimpleAdapter(
            this, // コンテキスト
            monsterData, // 表示したいデータ
            R.layout.list_item, // リストのレイアウト
            arrayOf("image","name", "from"), // 表示するデータのキー
            intArrayOf(R.id.itemImage, R.id.itemText, R.id.itemText2)// 表示する場所
        )

        // リストにAdapterを設定
        list.adapter = adapter

        // リストを押した時の処理
        list.setOnItemClickListener{ parent, view, position, id ->

        }
    }
}