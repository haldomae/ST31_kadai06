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
            MonsterData(
                "モンスター1",
                R.drawable.img1,
                "HAL東京30F"),
            MonsterData(
                "モンスター2",
                R.drawable.img2,
                "HAL東京29F"),
            MonsterData(
                "モンスター3",
                R.drawable.img3,
                "HAL東京28F"),
            MonsterData(
                "モンスター4",
                R.drawable.img4,
                "HAL東京27F"),
            MonsterData(
                "モンスター5",
                R.drawable.img5,
                "HAL東京26F"),
            MonsterData(
                "モンスター6",
                R.drawable.img6,
                "HAL東京25F"),


        )

        // Adapter
        // Adapterはリストとデータを仲介してくれるもの
//        val adapter = ArrayAdapter(
//            this, // コンテキスト(画面情報、どこの画面に表示するか)
//            android.R.layout.simple_list_item_1, // 項目のレイアウト
//            fruits // 表示するデータ
//        )
//        val adapter = SimpleAdapter(
//            this, // コンテキスト
//            monsterData, // 表示したいデータ
//            R.layout.list_item, // リストのレイアウト
//            arrayOf("image","name", "from"), // 表示するデータのキー
//            intArrayOf(R.id.itemImage, R.id.itemText, R.id.itemText2)// 表示する場所
//        )

        val adapter = CustomAdapter(
            this,
            monsterData
        )

        // リストにAdapterを設定
        list.adapter = adapter

        // リストを押した時の処理
        list.setOnItemClickListener{ parent, view, position, id ->

        }
    }
}