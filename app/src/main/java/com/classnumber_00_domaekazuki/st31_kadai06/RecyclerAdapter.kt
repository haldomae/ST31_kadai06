package com.classnumber_00_domaekazuki.st31_kadai06

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter(
    private val context: Context,
    private val listData: ArrayList<ListData>
): RecyclerView.Adapter<RecyclerViewHolder>() {

    // レイアウトを作成するInflater
    // LayoutInflaterとは、XMLレイアウトファイルをViewオブジェクトに変換するツール
    // list_item.xmlを実際の画面部品に変換してくれる
    private val inflater: LayoutInflater = LayoutInflater.from(context)

    // クリックリスナーの定義
    // 外部（MainActivity等）から設定可能なクリックリスナー
    var onItemClickListener: ((ListData) -> Unit)? = null

    // 新しいViewHolderを作成するメソッド
    // 初回表示時に画面表示分だけ呼ばれる
    // スクロール時に再利用できるViewHolderが無い場合のみ呼ばれる
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerViewHolder {
        // XMLからViewを作成
        // list_item.xmlを読み込んで実際のViewオブジェクトに変換
        val view = inflater.inflate(R.layout.list_item, parent, false)

        return RecyclerViewHolder(view)
    }

    // ViewHolderにデータを設定するメソッド
    // 項目が画面に表示される度に呼ばれる
    // スクロールでViewHolderが再利用される度に呼ばれる
    // 頻繁に呼ばれるため、処理は軽くする必要がある
    override fun onBindViewHolder(
        holder: RecyclerViewHolder,
        position: Int
    ) {
        // 【現在位置のデータを取得】
        val currentData = listData[position]

        // ViewHolderにデータを設定
        // ViewHolderのbind()メソッドに処理をさせる
        // これにより、データ設定の詳細はViewHolderが担当
        holder.bind(currentData)

        // クリックリスナーの設定
        if(onItemClickListener != null){
            holder.setClickListener(currentData, onItemClickListener!!)
        } else {
            holder.clearOnClickListener()
        }
    }

    override fun getItemCount(): Int {
        return listData.size
    }
}