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
        const val DATABASE_NAME = "todo_database.db"
        const val DATABASE_VERSION = 2  // バージョンアップ

        // テーブル設定
        const val TABLE_TODOS = "todos" // テーブル名

        // カラム定義
        const val COLUMN_ID = "id"
        const val COLUMN_TITLE = "title"        // タスクタイトル
        const val COLUMN_DESCRIPTION = "description" // タスク詳細
        const val COLUMN_IS_COMPLETED = "is_completed" // 完了フラグ
        const val COLUMN_PRIORITY = "priority"  // 優先度（1:高 2:中 3:低）

        // 【テーブル作成SQL】
        private const val CREATE_TABLE_TODOS = """
            CREATE TABLE $TABLE_TODOS (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_TITLE TEXT NOT NULL,
                $COLUMN_DESCRIPTION TEXT,
                $COLUMN_IS_COMPLETED INTEGER DEFAULT 0,
                $COLUMN_PRIORITY INTEGER DEFAULT 2
            )
        """

        // 【初期データ挿入SQL】
        private val INITIAL_TODOS = arrayOf(
            "INSERT INTO $TABLE_TODOS ($COLUMN_TITLE, $COLUMN_DESCRIPTION, $COLUMN_PRIORITY) VALUES ('買い物リストを作成', '今週末の買い物リストを作成する', 2)",
            "INSERT INTO $TABLE_TODOS ($COLUMN_TITLE, $COLUMN_DESCRIPTION, $COLUMN_PRIORITY) VALUES ('プロジェクト資料準備', 'プレゼン用の資料を準備する', 1)",
            "INSERT INTO $TABLE_TODOS ($COLUMN_TITLE, $COLUMN_DESCRIPTION, $COLUMN_PRIORITY) VALUES ('友人との約束', '来週の映画鑑賞の予定を確認', 3)",
            "INSERT INTO $TABLE_TODOS ($COLUMN_TITLE, $COLUMN_DESCRIPTION, $COLUMN_PRIORITY) VALUES ('部屋の掃除', 'リビングと寝室の掃除をする', 2)",
            "INSERT INTO $TABLE_TODOS ($COLUMN_TITLE, $COLUMN_DESCRIPTION, $COLUMN_PRIORITY) VALUES ('読書時間', '技術書を30ページ読む', 3)"
        )
    }

    override fun onCreate(p0: SQLiteDatabase?) {
        try {
            // 【テーブル作成】
            p0?.execSQL(CREATE_TABLE_TODOS)

            // 【初期データ挿入】
            p0?.beginTransaction()

            try {
                // 初期データを順次挿入
                for (insertSql in INITIAL_TODOS) {
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

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        try {
            // 【既存テーブル削除】
            p0?.execSQL("DROP TABLE IF EXISTS $TABLE_TODOS")

            // 【新しいテーブル作成】
            onCreate(p0)

        } catch (e: Exception) {
            e.printStackTrace()
            throw RuntimeException("データベースのアップグレードに失敗しました: ${e.message}")
        }
    }
}