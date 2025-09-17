package com.classnumber_00_domaekazuki.st31_kadai06

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase

// データベースを触るクラス
// CRUDを行う(Create, Read, Update, Delete)
class DatabaseRepository(private val context: Context) {
    // SQLiteHelperのインスタンスを作成
    private val dbHelper: DatabaseHelper = DatabaseHelper(context)

    // データ追加
    fun addData(name: String, image: String, habitat: String): Boolean{
        // 書き込み可能なDBを使用
        val db: SQLiteDatabase = dbHelper.writableDatabase
        // 処理成功可否フラグ
        var result = false
        try {
            // ContentValueの準備
            // INSERTする場合はオブジェクトにしなければいけない
            val value = ContentValues().apply {
                put(DatabaseHelper.DATABASE_NAME, name)
                put(DatabaseHelper.COLUMN_IMAGE, image)
                put(DatabaseHelper.COLUMN_HABITAT, habitat)
            }

            // INSERTを実行
            db.insert(DatabaseHelper.TABLE_NAME, null, value)

            // 成功したフラグに変更
            result = true
        }catch (e: Exception){
            throw e
            result = false
        }finally {
            db.close()
        }
        return result
    }

    // 指定されたデータ取得のメソッド
    @SuppressLint("Range")
    fun getDataById(id: Long): MonsterData?{
        // DB接続
        val db: SQLiteDatabase = dbHelper.readableDatabase
        // cursorを初期化
        var cursor: Cursor? = null

        // 取得するデータを入れる変数
        var monsterData: MonsterData? = null
        try {
            // データ取得
            cursor = db.query(
                DatabaseHelper.TABLE_NAME,
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

                // 画像リソースIDの取得
                val imageResourceId = context.resources.getIdentifier(
                    image,
                    "drawable",
                    context.packageName
                )
                monsterData = MonsterData(
                    name,
                    imageResourceId,
                    habitat
                )
            }
        }catch (e: Exception) {
            throw e
        }finally {
            // リソースの解放
            cursor?.close()
            db.close()
        }
        return monsterData
    }


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

    // データの更新
    fun updateData(id: Long, name: String, image: String, habitat: String): Boolean{
        val db: SQLiteDatabase = dbHelper.writableDatabase
        var result = false
        try {
            val values = ContentValues().apply {
                put(DatabaseHelper.TABLE_NAME, name)
                put(DatabaseHelper.COLUMN_IMAGE, image)
                put(DatabaseHelper.COLUMN_HABITAT, habitat)
            }
           db.update(
               DatabaseHelper.TABLE_NAME,
               values,
               "${DatabaseHelper.COLUMN_ID} = ?",
               arrayOf(id.toString())
           )
            result = true
        }catch (e: Exception){
            throw e
            result = false
        } finally {
            db.close()
        }
        return result
    }
}