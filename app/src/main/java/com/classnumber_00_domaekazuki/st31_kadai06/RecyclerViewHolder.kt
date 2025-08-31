package com.classnumber_00_domaekazuki.st31_kadai06

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// ViewHolderとはRecyclerViewでは必須のクラス
// 各リスト項目のView要素（ImageView、TextView等）への参照を保持する
// なぜ必要か
// findViewById()は重い処理なので、一度だけ実行して結果を保存することで,スクロール時のパフォーマンスを大幅に向上させる
// itemViewとは この項目全体のViewで、list_item.xmlから作成されたView全体を指す
class RecyclerViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val name = itemView.findViewById<TextView>(R.id.itemText)
    val image = itemView.findViewById<ImageView>(R.id.itemImage)
    val from = itemView.findViewById<TextView>(R.id.itemText2)

    // データを画面に表示するメソッド
    fun bind(listData: ListData){
        name.text = listData.name
        image.setImageResource(listData.image)
        from.text = listData.from
    }

    // クリックリスナーを設定するメソッド
    fun setClickListener(listData: ListData, clickListener: (ListData) -> Unit){
        itemView.setOnClickListener {
            clickListener(listData)
        }
    }

    // クリックリスナーを削除するメソッド
    // なぜ必要か?
    // RecyclerViewではViewが再利用されるため、前の項目のクリックリスナーが残っている可能性があり、防ぐ為に実装
    fun clearOnClickListener() {
        itemView.setOnClickListener(null)
    }
}