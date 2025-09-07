package com.classnumber_00_domaekazuki.st31_kadai06

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context): SQLiteOpenHelper(
    context, // アプリのコンテキスト
    DATABASE_NAME, // データベースファイル名
    null, // CursorFactory（通常null）
    DATABASE_VERSION // データベースのバージョン
) {
    companion object {
        // データベース設定
        // constキーワード：コンパイル時定数(パフォーマンス向上)
        // DBファイル名
        const val DATABASE_NAME = "monster_database.db"

        // DBバージョン(変更時は数値を上げる)
        const val DATABASE_VERSION = 1

        // テーブル設定
        const val TABLE_MONSTERS = "monsters" // テーブル名

        // カラム定義
        // プライマリーキー(自動増分ID)
        const val COLUMN_ID = "id"

        // 果物の名前
        const val COLUMN_NAME = "name"

        // 画像リソース名(img1, img2, ...)
        const val COLUMN_IMAGE = "image"

        // 生息地
        const val COLUMN_HABITAT = "habitat"

        // 【テーブル作成SQL
        // SQLiteの基本的なCREATE TABLE文
        private const val CREATE_TABLE_FRUITS = """
            CREATE TABLE $TABLE_MONSTERS (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NAME TEXT NOT NULL,
                $COLUMN_IMAGE INTEGER NOT NULL,
                $COLUMN_HABITAT TEXT NOT NULL
            )
        """

        // 【初期データ挿入SQL】
        // アプリ初回起動時に挿入する果物データ
        private val INITIAL_MONSTERS = arrayOf(
            "INSERT INTO $TABLE_MONSTERS ($COLUMN_NAME, $COLUMN_IMAGE, $COLUMN_HABITAT) VALUES ('モンスターNo1', 'img1', 'HAL東京30F')",
            "INSERT INTO $TABLE_MONSTERS ($COLUMN_NAME, $COLUMN_IMAGE, $COLUMN_HABITAT) VALUES ('モンスターNo2', 'img2', 'HAL東京29F')",
            "INSERT INTO $TABLE_MONSTERS ($COLUMN_NAME, $COLUMN_IMAGE, $COLUMN_HABITAT) VALUES ('モンスターNo3', 'img3', 'HAL東京28F')",
            "INSERT INTO $TABLE_MONSTERS ($COLUMN_NAME, $COLUMN_IMAGE, $COLUMN_HABITAT) VALUES ('モンスターNo4', 'img4', 'HAL東京27F')",
            "INSERT INTO $TABLE_MONSTERS ($COLUMN_NAME, $COLUMN_IMAGE, $COLUMN_HABITAT) VALUES ('モンスターNo5', 'img5', 'HAL東京26F')",

        )
    }

    /**
     * データベースが初回作成される時に呼ばれるメソッド
     *
     * 【呼ばれるタイミング】
     * - アプリを初回インストール後、初めてDBにアクセスした時
     * - DBファイルが削除された後、再度アクセスした時
     *
     * 【処理内容】
     * 1. テーブルを作成
     * 2. 初期データを挿入
     *
     * @param p0 作成されたSQLiteDatabaseのインスタンス
     */
    override fun onCreate(p0: SQLiteDatabase?) {
        try {
            // 【テーブル作成】
            // CREATE TABLE文を実行してfruitsテーブルを作成
            p0?.execSQL(CREATE_TABLE_FRUITS)

            // 【初期データ挿入】
            // 果物の初期データを一括挿入
            // トランザクションを使用して、全体の成功/失敗を保証
            p0?.beginTransaction()

            try {
                // 初期データを順次挿入
                for (insertSql in INITIAL_MONSTERS) {
                    p0?.execSQL(insertSql)
                }

                // 全ての挿入が成功した場合、トランザクションをコミット
                p0?.setTransactionSuccessful()

            } catch (e: Exception) {
                // エラーが発生した場合は自動的にロールバック
                throw e
            } finally {
                // トランザクション終了
                p0?.endTransaction()
            }

        } catch (e: Exception) {
            // データベース初期化エラーをログ出力
            e.printStackTrace()
            throw RuntimeException("データベースの初期化に失敗しました: ${e.message}")
        }
    }

    /**
     * データベースのバージョンアップ時に呼ばれるメソッド
     *
     * 【呼ばれるタイミング】
     * - DATABASE_VERSIONを上げてアプリを更新した時
     * - 既存ユーザーのDBを新しい構造に移行する必要がある時
     *
     * 【現在の実装】
     * シンプルに既存テーブルを削除して再作成
     * 本格的なアプリでは、データを保持したままスキーマ変更を行う
     *
     * @param p0 既存のSQLiteDatabaseのインスタンス
     * @param p1 現在のDBバージョン
     * @param 92 新しいDBバージョン
     */
    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        try {
            // 【既存テーブル削除】
            // DROP TABLE IF EXISTS を使用して安全に削除
            p0?.execSQL("DROP TABLE IF EXISTS $TABLE_MONSTERS")

            // 【新しいテーブル作成】
            // onCreate()を呼び出して新しい構造でテーブルを作成
            onCreate(p0)

        } catch (e: Exception) {
            e.printStackTrace()
            throw RuntimeException("データベースのアップグレードに失敗しました: ${e.message}")
        }
    }
}