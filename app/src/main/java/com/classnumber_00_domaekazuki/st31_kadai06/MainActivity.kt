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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

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
        // val listView: ListView = findViewById(R.id.listView)
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)

        // Repositoryの初期化
        // データベースアクセス用のRepositoryを作成
        val databaseRepository = DatabaseRepository(this)

        val monstersData = databaseRepository.getAllData()

        // 果物データのリストを作成（Map形式）
//        val ListData = arrayListOf(
//            ListData("モンスター1", R.drawable.img1,"HAL東京30F"),
//            ListData("モンスター2", R.drawable.img2,"HAL東京29F"),
//            ListData("モンスター3", R.drawable.img3,"HAL東京28F"),
//            ListData("モンスター4", R.drawable.img4,"HAL東京27F"),
//            ListData("モンスター5", R.drawable.img5,"HAL東京26F"),
//        )
        /*
        // ArrayAdapterを作成
        val adapter = SimpleAdapter(
            this,// コンテキスト
            ListData,
            R.layout.list_item,     // 標準のリスト項目レイアウト
            arrayOf("image", "name", "from"),              // データのキー
            intArrayOf(R.id.itemImage, R.id.itemText, R.id.itemText2)  // 対応するView ID
        )

        val adapter = CustomAdapter(this, ListData)

        // ListViewにAdapterを設定
        listView.adapter = adapter

        // リスト項目がクリックされた時の処理
        listView.setOnItemClickListener { parent, view, position, id ->
            val positionData = ListData[position]
            Toast.makeText(
                this,
                "選択されたモンスター: ${positionData.name}",
                Toast.LENGTH_SHORT
            ).show()
        }

         */

        // LayoutManagerの設定
        // RecyclerViewにはLayoutManagerが必須
        // LayoutManagerは「どのように項目を配置するか」を決定する
        // LinearLayoutManager: 縦または横の一列に項目を配置
        // 第2引数でORIENTATIONを指定可能（VERTICAL=縦、HORIZONTAL=横）
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        // アダプターの設定
        val adapter = RecyclerAdapter(this, monstersData)
        recyclerView.adapter = adapter

        adapter.onItemClickListener = {
            selectedData ->
            Toast.makeText(
                this,
                selectedData.name,
                Toast.LENGTH_SHORT
            ).show()
        }

    }


}