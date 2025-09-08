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
    // LayoutInflaterとは、xmlレイアウトファイルをViewオブジェクトに変換するツール
    // list_item.xmlを実際の画面部品に変換してくれる
    private val inflater: LayoutInflater
    = LayoutInflater.from(context)

    // 新しいViewHolderを作成するメソッド
    // 初回表示時に画面表示分だけ呼ばれる
    // スクロール時に再利用できるViewHolderがない場合のみ呼ばれる
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerViewHolder {
        // XmlからViewを作成
        val view = inflater.inflate(R.layout.list_item, parent, false)
        return RecyclerViewHolder(view)
    }

    // ViewHolderにデータを設定するメソッド
    // 項目が画面に表示される度に実行される
    // スクロールでViewHolderが再利用される度に実行される
    // 頻繁に実行される為。処理は軽くする
    override fun onBindViewHolder(
        holder: RecyclerViewHolder,
        position: Int
    ) {
        // 処理対象のデータ
        val currentData = listData[position]

        // ViewHolderにデータを設定
        // ViewHolderのbindメソッドに処理させる
        holder.bind(currentData)
    }

    // 項目数返却
    override fun getItemCount(): Int {
        return listData.size
    }
}