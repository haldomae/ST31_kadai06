package com.classnumber_00_domaekazuki.st31_kadai06

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

// Adapterとは、ListViewとデータの間を仲介する役割を持つクラス
// BaseAdapterとはAndroidが提供するアダプターの基本クラス
class CustomAdapter(
    private val context: Context,
    private val listData: ArrayList<ListData>
): BaseAdapter() {
    // リストの項目数を返却
    // この書き方でもよい
    // override fun getCount(): Int = listData.size
    override fun getCount(): Int {
        return listData.size
    }

    // 指定位置の項目を返却する
    override fun getItem(p0: Int): Any? {
        return listData[p0]
    }

    // 指定位置の項目IDを返却する
    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }
    // レイアウトを作成するInflater
    // LayoutInflaterとは、XMLレイアウトファイルをViewオブジェクトに変換するツール
    // list_item.xmlを実際の画面部品に変換してくれる
    private val inflater: LayoutInflater = LayoutInflater.from(context)

    // 各、項目リストのViewを作成
    // 画面に表示される項目分だけ実行される
    override fun getView(
        p0: Int, // 現在処理している項目の位置
        p1: View?, // 再利用可能な既存のView
        p2: ViewGroup? // このViewが追加される親のView(ListView)
    ): View? {
        // list_item.xmlファイルを実際のViewオブジェクトに変換
        val view = p1 ?: inflater.inflate(R.layout.list_item, p2, false)

        val name = view.findViewById<TextView>(R.id.itemText)
        val image = view.findViewById<ImageView>(R.id.itemImage)
        val from = view.findViewById<TextView>(R.id.itemText2)

        val currentData = listData[p0]

        name.text = currentData.name
        image.setImageResource(currentData.image)
        from.text = currentData.from

        return view
    }
}