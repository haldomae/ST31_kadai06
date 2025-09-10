package com.classnumber_00_domaekazuki.st31_kadai06

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase

// データベースを触るクラス
// CRUDを行う(Create, Read, Update, Delete)
class DatabaseRepository(private val context: Context) {
    // SQLiteHelperのインスタンスを作成
    private val dbHelper: DatabaseHelper = DatabaseHelper(context)

    // 全てのデータ取得のメソッド
    @SuppressLint("Range")
    fun getAllData(): ArrayList<MonsterData>{
        // データを入れる箱
        val monsterList = ArrayList<MonsterData>()

        // データベース接続
        // readableDatabaseは読み取り専用
        val db: SQLiteDatabase = dbHelper.readableDatabase

        // Cursorを用意
        // CursorはSQLiteの検索結果を順次読み取る為のオブジェクト
        var cursor: Cursor? = null
        try {
            // SELECT文の実行
            // query()メソッド実行
            cursor = db.query(
                DatabaseHelper.TABLE_NAME,// テーブル名
                null,// 取得するカラム
                null,// WHERE
                null,// WHEREの条件
                null,// GROUP BY
                null,// HAVING
                "${DatabaseHelper.COLUMN_ID} ASC"// ORDER BY
            )

            // 検索結果の処理
            // Cursorを使って結果を1行づつ読み取る
            // moveToFirst()は最初の行を取得
            if (cursor.moveToFirst()){
                do {
                   // データの読み取り
                    val id = cursor.getLong(cursor.getColumnIndex("id"))
                    val name = cursor.getString(cursor.getColumnIndex("name"))
                    val image = cursor.getString(cursor.getColumnIndex("image"))
                    val from = cursor.getString(cursor.getColumnIndex("habitat"))

                    // 画像リソースIDの取得
                    // 画像名からリソースIDに変換
                    val imageResourceId = context.resources.getIdentifier(
                        image,
                        "drawable",
                        context.packageName
                    )

                    // DBから取得したデータをMonsterDataオブジェクトにする
                    val monsterData = MonsterData(name, imageResourceId, from)

                    // 返却用のリストにまとめる
                    monsterList.add(monsterData)

                } while (cursor.moveToNext()) // 次の行がある限り繰り返す
            }

        } catch (e : Exception){
            throw e
        } finally {
            // リソースの解放
            cursor?.close()
            db.close()
        }
        return monsterList
    }
}