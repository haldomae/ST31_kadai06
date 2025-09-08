package com.classnumber_00_domaekazuki.st31_kadai06

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.RecyclerView

// ViewHolderはRecyclerViewでは必須
// 各リスト項目のView要素(ImageView, TextView)への参照を保持する
// なぜ必要か?
// findViewByIdは重い処理なので、一度だけ実行して結果を保持することでスクロール時のパフォーマンスを向上させる
// itemViewとは、この項目全体のViewでlist_itemから作成されたView全体を指す
class RecyclerViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    // 値をはめ込む場所を取得
    val name = itemView.findViewById<TextView>(R.id.itemText)
    val image = itemView.findViewById<ImageView>(R.id.itemImage)
    val from = itemView.findViewById<TextView>(R.id.itemText2)

    // データを画面に表示するメソッド
    fun bind(listData : ListData){
        name.text = listData.name
        image.setImageResource(listData.image)
        from.text = listData.from
    }
}