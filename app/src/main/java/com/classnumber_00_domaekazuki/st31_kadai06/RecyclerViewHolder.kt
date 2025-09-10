package com.classnumber_00_domaekazuki.st31_kadai06

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// ViewHolderとはRecyclerViewでは必須
// 各リスト項目のView要素(TextView、ImageView)への参照を保持する
// なぜ必要か
// findViewById()は重い処理なので、 一度実行して結果を保持する事で、 スクロール時のパフォーマンスを向上させる
// itemViewとは、この項目全体のViewで、list_item.xmlから作成されたもの
class RecyclerViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val name = itemView.findViewById<TextView>(R.id.itemText)
    val image = itemView.findViewById<ImageView>(R.id.itemImage)
    val from = itemView.findViewById<TextView>(R.id.itemText2)

    // データ表示メソッド
    fun bind(monsterData: MonsterData){
        name.text = monsterData.name
        image.setImageResource(monsterData.image)
        from.text = monsterData.from
    }
}