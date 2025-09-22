package com.classnumber_00_domaekazuki.st31_kadai06

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase

// データベースへのアクセスを管理するクラス
// CRUD操作(Create, Read, Update, Delete)を行う
class DatabaseRepository(private val context: Context) {
    // SQLiteHelperのインスタンス
    private val dbHelper: DatabaseHelper = DatabaseHelper(context)

    // データの追加
    fun addData(name: String, image: String, habitat: String): Boolean{
        // 書き込み可能なDB
        val db: SQLiteDatabase = dbHelper.writableDatabase

        // 処理成功可否フラグ
        var result = false
        try {
            // ContentValueの準備
            // INSERTする場合はオブジェクトにしなければいけない
            val value = ContentValues().apply{
                put(DatabaseHelper.COLUMN_NAME, name)
                put(DatabaseHelper.COLUMN_IMAGE, image)
                put(DatabaseHelper.COLUMN_HABITAT, habitat)
            }
            // INSERT文実行
            db.insert(DatabaseHelper.TABLE_MONSTERS, null, value)

            // 成功フラグに変更
            result = true
        }catch (e: Exception){
            throw e
            result = false
        }finally {
            db.close()
        }
        return result
    }

    // 指定されたIDからデータ取得
    @SuppressLint("Range")
    fun getDataById(id: Long): ListData?{
        // DB接続
        val db: SQLiteDatabase = dbHelper.readableDatabase
        // Cursorを初期化
        var cursor: Cursor? = null

        // 取得データを格納する変数
        var listData: ListData? = null
        try {
            // データ取得
            cursor = db.query(
                DatabaseHelper.TABLE_MONSTERS,
                null,
                "${DatabaseHelper.COLUMN_ID} = ?",
                arrayOf(id.toString()),
                null,
                null,
                null
            )
            if(cursor.moveToFirst()){
                val name = cursor.getString(cursor.getColumnIndex("name"))
                val image = cursor.getString(cursor.getColumnIndex("image"))
                val habitat = cursor.getString(cursor.getColumnIndex("habitat"))

                // 画像名からリソースIDを取得
                val imageResourceId = context.resources.getIdentifier(
                    image,
                    "drawable",
                    context.packageName
                )
                listData = ListData(
                    name,
                    imageResourceId,
                    habitat
                )
            }
        }catch (e : Exception){
            throw e
        }finally {
            // リソースを解放
            cursor?.close()
            db.close()
        }
        return listData
    }
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

    // データ更新処理
    fun updateData(id: Long, name: String, image: String, habitat: String): Boolean{
        // 書き込み可能なDB
        val db: SQLiteDatabase = dbHelper.writableDatabase
        // 処理成功可否フラグ
        var result = false
        try {
            val value = ContentValues().apply {
                put(DatabaseHelper.COLUMN_NAME, name)
                put(DatabaseHelper.COLUMN_IMAGE, image)
                put(DatabaseHelper.COLUMN_HABITAT, habitat)
            }
            // データ更新処理
            db.update(
                DatabaseHelper.TABLE_MONSTERS,
                value,
                "${DatabaseHelper.COLUMN_ID} = ?",
                arrayOf(id.toString())
            )
            result = true
        }catch (e: Exception){
            throw e
            result = false
        }finally {
            db.close()
        }
        return result
    }
}