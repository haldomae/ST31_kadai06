package com.classnumber_00_domaekazuki.st31_kadai06

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context): SQLiteOpenHelper(
    context, // コンテキスト
    DATABASE_NAME,// データベース名
    null, // CursorFactory(基本的にnull)
    DATABASE_VERSION // データベースのバージョン
) {
    companion object{
        // データベースの設定
        // DBファイル名
        // constとはコンパイル時定数(パフォーマンス向上)
        const val DATABASE_NAME = "monster_database.db"

        // DBバージョン
        // DBの構造が変更された時はバージョン変更する
        const val DATABASE_VERSION = 1

        // テーブル設定
        const val TABLE_MONSTERS = "monsters" // モンスターズ

        // カラム設定
        // プライマリーキー(自動連番)
        const val COLUMN_ID = "id" // テーブル名
        // モンスター名
        const val COLUMN_NAME = "name"
        // 画像名
        const val COLUMN_IMAGE = "image"
        // 生息地
        const val COLUMN_HABITAT = "habitat"

        // テーブル作成のSQL
        private const val CREATE_TABLE_MONSTERS = """
            CREATE TABLE $TABLE_MONSTERS (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NAME TEXT NOT NULL,
                $COLUMN_IMAGE TEXT NOT NULL,
                $COLUMN_HABITAT TEXT NOT NULL
            )
        """

        // 初期データ挿入SQL
        // アプリ起動時に挿入するデータ
        private val INSERT_MONSTERS = arrayOf(
            "INSERT INTO $TABLE_MONSTERS ($COLUMN_NAME, $COLUMN_IMAGE, $COLUMN_HABITAT) VALUES('モンスターNo01', 'img1', 'HAL東京30F')",
            "INSERT INTO $TABLE_MONSTERS ($COLUMN_NAME, $COLUMN_IMAGE, $COLUMN_HABITAT) VALUES('モンスターNo02', 'img2', 'HAL東京29F')",
            "INSERT INTO $TABLE_MONSTERS ($COLUMN_NAME, $COLUMN_IMAGE, $COLUMN_HABITAT) VALUES('モンスターNo03', 'img3', 'HAL東京28F')",
            "INSERT INTO $TABLE_MONSTERS ($COLUMN_NAME, $COLUMN_IMAGE, $COLUMN_HABITAT) VALUES('モンスターNo04', 'img4', 'HAL東京27F')",
            "INSERT INTO $TABLE_MONSTERS ($COLUMN_NAME, $COLUMN_IMAGE, $COLUMN_HABITAT) VALUES('モンスターNo05', 'img5', 'HAL東京26F')",
            "INSERT INTO $TABLE_MONSTERS ($COLUMN_NAME, $COLUMN_IMAGE, $COLUMN_HABITAT) VALUES('モンスターNo06', 'img6', 'HAL東京25F')",

        )
    }

    // データベースが初回作成された時に呼ばれるメソッド
    // 呼ばれるタイミング
    // - アプリ初回インストール時、始めてDBアクセスした時
    // - DBファイルが削除された時、再度アクセスした時
    // 処理内容
    // 1. テーブル作成
    // 2. 初期データの挿入
    // p0は作成されたSQLiteDatabaseのインスタンス
    override fun onCreate(p0: SQLiteDatabase?) {
        try {
            // テーブル作成
            p0?.execSQL(CREATE_TABLE_MONSTERS)

            // 初期データを一括追加
            // トランザクション開始
            p0?.beginTransaction()
            try {
                // データを順次追加
                for(insetSql in INSERT_MONSTERS){
                    p0?.execSQL(insetSql)
                }
                // 全ての挿入が完了した場合、トランザクションをコミット
                p0?.setTransactionSuccessful()
            } catch (e: Exception){
                throw e
            } finally {
                // トランザクション終了
                p0?.endTransaction()
            }
        }catch (e: Exception){
            throw e
        }
    }

    // データベースのバージョンアップ時に実行される
    // 呼ばれるタイミング
    // - DATABASE_VERSIONをあげてアプリを更新した時
    // - 既存ユーザのDBを新しい構造に移行する必要があるとき
    // p0 既存のSQLiteDatabaseのインスタンス
    // p1 現在のDBバージョン
    // p2 新しいDBバージョン
    override fun onUpgrade(
        p0: SQLiteDatabase?,
        p1: Int,
        p2: Int
    ) {
        try {
            // テーブル削除
            p0?.execSQL("DROP TABLE IF EXISTS $TABLE_MONSTERS")

            // 新しいテーブル作成
            onCreate(p0)
        }catch (e: Exception){
            throw e
        }

    }
}