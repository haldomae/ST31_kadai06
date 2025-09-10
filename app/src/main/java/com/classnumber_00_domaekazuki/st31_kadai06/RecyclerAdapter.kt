package com.classnumber_00_domaekazuki.st31_kadai06

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter(
    private val context: Context,
    private val listData: ArrayList<MonsterData>
): RecyclerView.Adapter<RecyclerViewHolder>() {

    // レイアウトを作成するInflater
    // LayoutInflaterとは、XMLレイアウトファイルをViewオブジェクトに変換する
    // list_item.xmlを実際の画面部品に変換してくれる
    private val inflater: LayoutInflater = LayoutInflater.from(context)


    // 新しいViewHolderを作成する
    // 初回表示時に呼ばれる
    // スクロール時に再利用できるViewHolderがない場合のみ呼ばれる
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
        // 現在位置(処理対象)のデータを取得
        val currentData = listData[position]

        // ViewHolderにデータを設定
        // ViewHolderのbindを呼び出す
        holder.bind(currentData)
    }

    // 項目数を返却
    override fun getItemCount(): Int {
        return listData.size
    }
}