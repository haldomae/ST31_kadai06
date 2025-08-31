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
        val listView: ListView = findViewById(R.id.listView)
        // 果物データのリストを作成（Map形式）
        val ListData = arrayListOf(
            mapOf("name" to "モンスター1", "image" to R.drawable.img1,"from" to "HAL東京30F"),
            mapOf("name" to "モンスター2", "image" to R.drawable.img2,"from" to "HAL東京29F"),
            mapOf("name" to "モンスター3", "image" to R.drawable.img3,"from" to "HAL東京28F"),
            mapOf("name" to "モンスター4", "image" to R.drawable.img4,"from" to "HAL東京27F"),
            mapOf("name" to "モンスター5", "image" to R.drawable.img5,"from" to "HAL東京26F"),
        )

        // ArrayAdapterを作成
        val adapter = SimpleAdapter(
            this,// コンテキスト
            ListData,
            R.layout.list_item,     // 標準のリスト項目レイアウト
            arrayOf("image", "name", "from"),              // データのキー
            intArrayOf(R.id.itemImage, R.id.itemText, R.id.itemText2)  // 対応するView ID
        )

        // ListViewにAdapterを設定
        listView.adapter = adapter

        // リスト項目がクリックされた時の処理
        listView.setOnItemClickListener { parent, view, position, id ->
            val positionData = ListData[position]
            Toast.makeText(
                this,
                "選択されたモンスター: ${positionData["name"]}",
                Toast.LENGTH_SHORT
            ).show()
        }

    }


}