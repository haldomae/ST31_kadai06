package com.classnumber_00_domaekazuki.st31_kadai06

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase

// データベースへのアクセスを管理するクラス
// CRUD操作(Create, Read, Update, Delete)を行う
class DatabaseRepository(private val context: Context) {
    // SQLiteHelperのインスタンス
    private val dbHelper: DatabaseHelper = DatabaseHelper(context)

    // 全てのデータを取得するメソッド
    @SuppressLint("Range")
    fun getAllData(): ArrayList<ListData>{
        val monsterList = ArrayList<ListData>()

        // データベース接続
        // readableDatabaseは読み取り専用(パフォーマンス向上)
        val db: SQLiteDatabase = dbHelper.readableDatabase

        // Cursorを宣言
        // Cursorは検索結果を順次読み込むためのオブジェクト
        var cursor: Cursor? = null

        try {
            // SELECTを実行
            // queryメソッドでSELECTを実行
            cursor = db.query(
                DatabaseHelper.TABLE_MONSTERS,// テーブル名
                null,// 取得するカラム
                null,// WHERE句
                null,// WHERE句の引数
                null,// GROUP BY句
                null,// HAVING句
                "${DatabaseHelper.COLUMN_ID} ASC"// ORDER BY句
            )
            // 1件ずつデータ読み取り
            if(cursor.moveToFirst()){
                do {
                    // データ読み取り
                    val id = cursor.getLong(cursor.getColumnIndex("id"))
                    val name = cursor.getString(cursor.getColumnIndex("name"))
                    val image = cursor.getString(cursor.getColumnIndex("image"))
                    val from = cursor.getString(cursor.getColumnIndex("habitat"))

                    // 画像リソースIDを取得
                    // 画像名img1からリソースIDを取得
                    val imageResourceId = context.resources.getIdentifier(
                        image,
                        "drawable",
                        context.packageName
                    )

                    // モンスターデータに格納
                    val monsterData = ListData(name, imageResourceId, from)
                    monsterList.add(monsterData)
                }while (cursor.moveToNext()) // 次の行が存在する限り継続
            }

        }catch (e: Exception){
            throw e
        }finally {
            // リソースの解放
            cursor?.close()
            db.close()
        }
        return monsterList
    }
}