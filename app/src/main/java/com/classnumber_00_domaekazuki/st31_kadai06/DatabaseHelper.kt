package com.classnumber_00_domaekazuki.st31_kadai06

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context): SQLiteOpenHelper(
    context, // コンテキスト
    DATABASE_NAME, // データベースのファイル名
    null, // CursorFactory(基本的にnull)
    DATABASE_VERSION // データベースのバージョン
) {

    companion object{
        // データベースの設定
        // constはコンパイル時定数(パフォーマンスが向上)
        // DBファイル名
        const val DATABASE_NAME = "monster_database.db"

        // DBバージョン(DBの構造を変更した時に数値を変更させる)
        const val DATABASE_VERSION = 1

        // テーブル設定
        // テーブル名
        const val TABLE_NAME = "monsters"

        // カラム設定
        // プライマリーキー(自動連番)
        const val COLUMN_ID = "id"

        // モンスターの名前
        const val COLUMN_NAME = "name"

        // モンスターの画像
        const val COLUMN_IMAGE = "image"

        // モンスターの生息地
        const val COLUMN_HABITAT = "habitat"

        // テーブル作成のSQL
        private const val CREATE_TABLE_MONSTERS = """
            CREATE TABLE $TABLE_NAME (
            $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
            $COLUMN_NAME TEXT NOT NULL,
            $COLUMN_IMAGE TEXT NOT NULL,
            $COLUMN_HABITAT TEXT NOT NULL
            )
        """

        // 初期データ挿入SQL
        private val INSERT_MONSTERS = arrayOf(
            "INSERT INTO $TABLE_NAME ($COLUMN_NAME, $COLUMN_IMAGE, $COLUMN_HABITAT) VALUES('モンスターNo01', 'img1', 'コクーンタワー50F')",
            "INSERT INTO $TABLE_NAME ($COLUMN_NAME, $COLUMN_IMAGE, $COLUMN_HABITAT) VALUES('モンスターNo02', 'img2', 'コクーンタワー49F')",
            "INSERT INTO $TABLE_NAME ($COLUMN_NAME, $COLUMN_IMAGE, $COLUMN_HABITAT) VALUES('モンスターNo03', 'img3', 'コクーンタワー48F')",
            "INSERT INTO $TABLE_NAME ($COLUMN_NAME, $COLUMN_IMAGE, $COLUMN_HABITAT) VALUES('モンスターNo04', 'img4', 'コクーンタワー47F')",
            "INSERT INTO $TABLE_NAME ($COLUMN_NAME, $COLUMN_IMAGE, $COLUMN_HABITAT) VALUES('モンスターNo05', 'img5', 'コクーンタワー46F')",
            "INSERT INTO $TABLE_NAME ($COLUMN_NAME, $COLUMN_IMAGE, $COLUMN_HABITAT) VALUES('モンスターNo06', 'img6', 'コクーンタワー45F')",
        )

    }

    // データベースが初回作成される時に呼ばれるメソッド
    // 呼ばれるタイミング
    // - アプリ初回インストール時、始めてDBにアクセスした時
    // - DBファイルが削除されたとき
    // ここで行う処理
    // - DB作成
    // - 初期データの挿入
    // p0は作成されたSQLiteのインスタンス
    override fun onCreate(p0: SQLiteDatabase?) {
        try {
            // テーブル作成
            p0?.execSQL(CREATE_TABLE_MONSTERS)

            // 初期データ挿入
            // トランザクション開始
            p0?.beginTransaction()
            try {
                // 1件づつデータを挿入
                for(insertSql in INSERT_MONSTERS){
                    p0?.execSQL(insertSql)
                }
                // 処理が終了したら、トランザクションをコミット
                p0?.setTransactionSuccessful()
            }catch (e: Exception){
                throw e
            }finally {
                // トランザクション終了
                p0?.endTransaction()
            }

        }catch (e: Exception){
            throw e
        }
    }

    // DBのバージョンアップ時に呼ばれるメソッド
    // 呼ばれるタイミング
    // - DATABASE_VERSIONをあげてアプリを更新した時
    // p0は現在(新しい)のSQLiteDatabaseのインスタンス
    // p1は現在のDBバージョン
    // p2は新しいDBバージョン
    override fun onUpgrade(
        p0: SQLiteDatabase?,
        p1: Int,
        p2: Int
    ) {
        try {
            // 現在のテーブル削除
            p0?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")

            // 新しくテーブル作成
            onCreate(p0)
        }catch (e: Exception){
            throw e
        }
    }
}