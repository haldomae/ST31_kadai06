package com.classnumber_00_domaekazuki.st31_kadai06

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

// Adapterとは、ListViewとデータの間を仲介する役割
// BaseAdapterとはAndroidが提供している基本的なAdapter
class CustomAdapter(
    private val context: Context, // コンテキスト
    private val listData: ArrayList<MonsterData>// 表示するデータ
): BaseAdapter() {
    // リストの項目数を返却
    override fun getCount(): Int {
        return listData.size
    }
    // 指定位置の項目を返却
    override fun getItem(p0: Int): Any? {
        return listData[p0]
    }
    // 指定位置の項目IDを返却
    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    // レイアウトを作るにはInflaterが必要
    // LayoutInflaterとは、XMLレイアウトファイルをオブジェクトに変換してくれる
    private val inflater: LayoutInflater = LayoutInflater.from(context)

    // 各項目のView(1行)を作成
    // 行数分実行される
    override fun getView(
        p0: Int, // 現在処理している項目の位置
        p1: View?, // 再利用可能な既存のView
        p2: ViewGroup? // このViewが追加される親のView(ListView)
    ): View? {
        val view = p1 ?: inflater.inflate(
            R.layout.list_item,
            p2,
            false
        )

        // 項目を設定する場所を取得
        val name = view
            .findViewById<TextView>(R.id.itemText)
        val image = view
            .findViewById<ImageView>(R.id.itemImage)
        val from = view
            .findViewById<TextView>(R.id.itemText2)

        // 現在処理している項目を取得
        val currentData = listData[p0]

        // データを設定
        name.text = currentData.name
        image.setImageResource(currentData.image)
        from.text = currentData.from

        // 部品を返却
        return view
    }
}