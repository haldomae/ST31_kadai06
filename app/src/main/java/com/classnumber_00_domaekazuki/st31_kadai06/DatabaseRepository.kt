package com.classnumber_00_domaekazuki.st31_kadai06

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase

/**
 * データへのアクセスを管理するRepositoryクラス
 *
 * Repositoryパターンとは
 * データの取得方法（SQLite、API、ファイル等）を抽象化するデザインパターン
 * 「データがどこにあるかを隠し、統一されたインターフェースを提供」
 *
 * なぜ必要
 * - データアクセスロジックの集約
 * - ActivityやFragmentから直接SQLiteを触らせない
 * - 将来的にAPIやRoomに変更する際の影響を最小化
 * - テストが書きやすくなる
 *
 * 主な機能
 * - CRUD操作（Create, Read, Update, Delete）の提供
 * - SQLiteの複雑な処理を隠蔽
 * - 安全なデータアクセス
 */
class DatabaseRepository(private val context: Context) {
    // SQLiteHelperのインスタンス
    // データベースへの接続・操作を管理
    private val dbHelper: DatabaseHelper = DatabaseHelper(context)
    /**
     * 新しいデータを追加するメソッド
     *
     * 【処理の流れ】
     * 1. データベースに接続（書き込み可能）
     * 2. ContentValuesに値をセット
     * 3. INSERT文でデータを挿入
     * 4. 成功可否を返却
     * 5. リソースの解放
     *
     * @param name モンスター名
     * @param image 画像名（例："img1"）
     * @param habitat 生息地
     * @return 追加に成功した場合true、失敗した場合false
     */
    fun addData(name: String, image: String, habitat: String): Boolean {
        // 【データベース接続】
        // writableDatabase：書き込み可能でDBを開く
        val db: SQLiteDatabase = dbHelper.writableDatabase
        var result = false

        try {
            // 【ContentValuesの準備】
            // INSERT文で挿入するデータを格納するオブジェクト
            val values = ContentValues().apply {
                put(DatabaseHelper.COLUMN_NAME, name)
                put(DatabaseHelper.COLUMN_IMAGE, image)
                put(DatabaseHelper.COLUMN_HABITAT, habitat)
            }

            // 【INSERT文の実行】
            // insert()メソッドで新しいレコードを追加
            // 戻り値：新しいレコードのID（-1の場合は失敗）
            val newRowId = db.insert(DatabaseHelper.TABLE_MONSTERS, null, values)

            // 【結果の判定】
            result = newRowId != -1L

        } catch (e: Exception) {
            // エラー処理
            e.printStackTrace()
            result = false

        } finally {
            // 【リソースの解放】
            db.close()
        }

        return result
    }
    /**
     * 指定されたIDのデータを取得するメソッド
     *
     * 【処理の流れ】
     * 1. データベースに接続
     * 2. SELECT文でIDに該当するデータを取得
     * 3. CursorからListDataオブジェクトに変換
     * 4. オブジェクトを返却
     * 5. リソースの解放
     *
     * @param id 取得対象のID
     * @return 該当するListDataオブジェクト（見つからない場合はnull）
     */
    @SuppressLint("Range")
    fun getDataById(id: Long): ListData? {
        val db: SQLiteDatabase = dbHelper.readableDatabase
        var cursor: Cursor? = null
        var listData: ListData? = null

        try {
            // 【SELECT文の実行】
            // IDを指定して特定のレコードを取得
            cursor = db.query(
                DatabaseHelper.TABLE_MONSTERS,     // テーブル名
                null,                              // 取得するカラム（nullは全カラム）
                "${DatabaseHelper.COLUMN_ID} = ?", // WHERE句
                arrayOf(id.toString()),            // WHERE句の引数
                null,                              // GROUP BY句
                null,                              // HAVING句
                null                               // ORDER BY句
            )

            // 【検索結果の処理】
            if (cursor.moveToFirst()) {
                val name = cursor.getString(cursor.getColumnIndex("name"))
                val image = cursor.getString(cursor.getColumnIndex("image"))
                val habitat = cursor.getString(cursor.getColumnIndex("habitat"))

                // 【画像リソースIDの取得】
                val imageResourceId = context.resources.getIdentifier(
                    image,
                    "drawable",
                    context.packageName
                )

                // 【ListDataオブジェクトの作成】
                listData = ListData(name, imageResourceId, habitat)
            }

        } catch (e: Exception) {
            // エラー処理
            e.printStackTrace()

        } finally {
            // 【リソースの解放】
            cursor?.close()
            db.close()
        }

        return listData
    }
    /**
     * 全てのデータを取得するメソッド
     *
     * 【戻り値】
     * ArrayList<ListData>：RecyclerViewで直接使用可能な形式
     *
     * 【処理の流れ】
     * 1. データベースに接続
     * 2. SELECT文でデータを取得
     * 3. CursorからFruitオブジェクトに変換
     * 4. リストとして返却
     * 5. リソースの解放
     *
     * @return 全果物データのリスト
     */
    @SuppressLint("Range")
    fun getAllData(): ArrayList<ListData> {
        val monsterList = ArrayList<ListData>()

        // 【データベース接続】
        // readableDatabase：読み取り専用でDBを開く（パフォーマンス向上）
        val db: SQLiteDatabase = dbHelper.readableDatabase

        // 【Cursor変数の宣言】
        // Cursor：SQLiteの検索結果を順次読み取るためのオブジェクト
        var cursor: Cursor? = null

        try {
            // 【SELECT文の実行】
            // query()メソッドでSELECT文を実行
            cursor = db.query(
                DatabaseHelper.TABLE_MONSTERS,  // テーブル名
                null, // 取得するカラム（nullは全カラム）
                null, // WHERE句（nullは条件なし）
                null, // WHERE句の引数
                null, // GROUP BY句
                null, // HAVING句
                "${DatabaseHelper.COLUMN_ID} ASC"  // ORDER BY句（ID昇順）
            )

            // 【検索結果の処理】
            // Cursorを使って結果を一行ずつ読み取り
            if (cursor.moveToFirst()) {
                do {
                    // 【データの読み取り】
                    val id = cursor.getLong(cursor.getColumnIndex("id"))
                    val name = cursor.getString(cursor.getColumnIndex("name"))
                    val image = cursor.getString(cursor.getColumnIndex("image"))
                    val from = cursor.getString(cursor.getColumnIndex("habitat"))

                    // 【画像リソースIDの取得】
                    // 画像名（"img1"）からリソースID（R.drawable.img1）に変換
                    val imageResourceId = context.resources.getIdentifier(
                        image,
                        "drawable",
                        context.packageName
                    )

                    // 【Fruitオブジェクトの作成】
                    // DBから取得したデータでFruitオブジェクトを作成
                    val monsterData = ListData(name, imageResourceId, from)

                    // 【リストに追加】
                    monsterList.add(monsterData)

                } while (cursor.moveToNext()) // 次の行が存在する限り繰り返し
            }

        } catch (e: Exception) {
            // エラー処理
            e.printStackTrace()

        } finally {
            // 【リソースの解放】
            // 必ずcursorとdbを閉じてメモリリークを防ぐ
            cursor?.close()
            db.close()
        }

        return monsterList
    }
}