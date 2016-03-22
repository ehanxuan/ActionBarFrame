package com.ehx.actionbarframe.utils;

import android.util.Log;

/**
 * ログ出力用のクラス。アプリ内のログは全てこのクラスを経由し、個別に出力することがないようにする。
 * 本クラス内のフラグを書き換えることでログの出力有無を切り替えることができる。 ログはクラス名、メソッド名、ライン番号を自動的に付与して出力する。
 * <<className#methodName:lineNumber>>
 */
public class SDLogger {

    // **********************************************************************
    // 定数
    // **********************************************************************

    /**
     * APIログ出力時のタグ。
     */
    private static final String LOG_TAG_API = "ProjectName_Api";

    /**
     * アプリのログ出力時のタグ。
     */
    private static final String LOG_TAG_APPLI = "ProjectName_App";

    // **********************************************************************
    // メンバ
    // **********************************************************************

    /**
     * ログ出力フラグ。
     */
    private static boolean mIsShowLog = false;

    // **********************************************************************
    // パブリックメソッド
    // **********************************************************************

    /**
     * ログ出力フラグを切り替える。
     *
     * @param isShowLog ログを表示するか否か
     */
    public static void setShowLog(boolean isShowLog) {
        mIsShowLog = isShowLog;
    }

    // ----------------------------------------------------------------------
    // パブリックメソッド(API)
    // ----------------------------------------------------------------------

    /**
     * APIからのログを出力する。API用。Infoのみ。
     */
    public static void api() {
        outputLog(Log.INFO, LOG_TAG_API, null, null);
    }

    /**
     * APIからのログを出力する。API用。Infoのみ。
     *
     * @param message メッセージ
     */
    public static void api(String message) {
        outputLog(Log.INFO, LOG_TAG_API, message, null);
    }

    // ----------------------------------------------------------------------
    // パブリックメソッド(Verbose)
    // ----------------------------------------------------------------------

    /**
     * アプリからのログを出力する。Verbose。
     */
    public static void v() {
        outputLog(Log.VERBOSE, LOG_TAG_APPLI, null, null);
    }

    /**
     * アプリからのログを出力する。Verbose。
     *
     * @param message メッセージ
     */
    public static void v(String message, Object... args) {
        outputLog(Log.VERBOSE, LOG_TAG_APPLI, maybeFormat(message, args), null);
    }

    /**
     * アプリからのログを出力する。Verbose。
     *
     * @param message メッセージ
     * @param throwable 例外
     */
    public static void v(Throwable throwable, String message, Object... args) {
        outputLog(Log.VERBOSE, LOG_TAG_APPLI, maybeFormat(message, args), throwable);
    }

    // ----------------------------------------------------------------------
    // パブリックメソッド(Debug)
    // ----------------------------------------------------------------------

    /**
     * アプリからのログを出力する。Debug。
     */
    public static void d() {
        outputLog(Log.DEBUG, LOG_TAG_APPLI, null, null);
    }

    /**
     * アプリからのログを出力する。Debug。
     *
     * @param message メッセージ
     */
    public static void d(String message, Object... args) {
        outputLog(Log.DEBUG, LOG_TAG_APPLI, maybeFormat(message, args), null);
    }

    /**
     * アプリからのログを出力する。Debug。
     *
     * @param message メッセージ
     * @param throwable 例外
     */
    public static void d(Throwable throwable, String message, Object... args) {
        outputLog(Log.DEBUG, LOG_TAG_APPLI, maybeFormat(message, args), throwable);
    }

    // ----------------------------------------------------------------------
    // パブリックメソッド(Info)
    // ----------------------------------------------------------------------

    /**
     * アプリからのログを出力する。Info。
     */
    public static void i() {
        outputLog(Log.INFO, LOG_TAG_APPLI, null, null);
    }

    /**
     * アプリからのログを出力する。Info。
     *
     * @param message メッセージ
     */
    public static void i(String message, Object... args) {
        outputLog(Log.INFO, LOG_TAG_APPLI, maybeFormat(message, args), null);
    }

    /**
     * アプリからのログを出力する。Info。
     *
     * @param message メッセージ
     * @param throwable 例外
     */
    public static void i(Throwable throwable, String message, Object... args) {
        outputLog(Log.INFO, LOG_TAG_APPLI, maybeFormat(message, args), throwable);
    }

    // ----------------------------------------------------------------------
    // パブリックメソッド(Warning)
    // ----------------------------------------------------------------------

    /**
     * アプリからのログを出力する。Warning。
     */
    public static void w() {
        outputLog(Log.WARN, LOG_TAG_APPLI, null, null);
    }

    /**
     * アプリからのログを出力する。Warning。
     *
     * @param message メッセージ
     */
    public static void w(String message, Object... args) {
        outputLog(Log.WARN, LOG_TAG_APPLI, maybeFormat(message, args), null);
    }

    /**
     * アプリからのログを出力する。Warning。
     *
     * @param message メッセージ
     * @param throwable 例外
     */
    public static void w(Throwable throwable, String message, Object... args) {
        outputLog(Log.WARN, LOG_TAG_APPLI, maybeFormat(message, args), throwable);
    }

    // ----------------------------------------------------------------------
    // パブリックメソッド(Error)
    // ----------------------------------------------------------------------

    /**
     * アプリからのログを出力する。Error。
     */
    public static void e() {
        outputLog(Log.ERROR, LOG_TAG_APPLI, null, null);
    }

    /**
     * アプリからのログを出力する。Error。
     *
     * @param message メッセージ
     */
    public static void e(String message, Object... args) {
        outputLog(Log.ERROR, LOG_TAG_APPLI, maybeFormat(message, args), null);
    }

    /**
     * アプリからのログを出力する。Error。
     *
     * @param message メッセージ
     * @param throwable 例外
     */
    public static void e(Throwable throwable, String message, Object... args) {
        outputLog(Log.ERROR, LOG_TAG_APPLI, maybeFormat(message, args), throwable);
    }

    // **********************************************************************
    // プライベートメソッド
    // **********************************************************************

    // ----------------------------------------------------------------------
    // プライベートメソッド(LogCat)
    // ----------------------------------------------------------------------

    /**
     * ログを出力する。
     *
     * @param type ログの種類
     * @param tag タグ
     * @param message メッセージ、null可
     * @param throwable 例外、null可
     */
    private static void outputLog(int type, String tag, String message, Throwable throwable) {
        if (!mIsShowLog) {
            return;
        }

        if (message == null) {
            message = getStackTraceInfo();
        } else {
            message = getStackTraceInfo() + message;
        }

        switch (type) {
            case Log.VERBOSE:
                if (throwable == null) {
                    Log.v(tag, message);
                } else {
                    Log.v(tag, message, throwable);
                }
                break;

            case Log.DEBUG:
                if (throwable == null) {
                    Log.d(tag, message);
                } else {
                    Log.d(tag, message, throwable);
                }
                break;

            case Log.INFO:
                if (throwable == null) {
                    Log.i(tag, message);
                } else {
                    Log.i(tag, message, throwable);
                }
                break;

            case Log.WARN:
                if (throwable == null) {
                    Log.w(tag, message);
                } else {
                    Log.w(tag, message, throwable);
                }
                break;

            case Log.ERROR:
                if (throwable == null) {
                    Log.e(tag, message);
                } else {
                    Log.e(tag, message, throwable);
                }
        }
    }

    /**
     * スタックトレースから呼び出し元の基本情報を取得。出力例、"<<className#methodName:lineNumber>>"
     *
     * @return クラス、メソッド、ライン数のString情報
     */
    private static String getStackTraceInfo() {
        // 現在のスタックトレースを取得。
        // 0:VM 1:スレッド 2:getStackTraceInfo() 3:outputLog() 4:logDebug()等 5:呼び出し元
        StackTraceElement element = Thread.currentThread().getStackTrace()[5];

        String fullName = element.getClassName();
        String className = fullName.substring(fullName.lastIndexOf(".") + 1);
        String methodName = element.getMethodName();
        int lineNumber = element.getLineNumber();

        return "<<" + className + "#" + methodName + ":" + lineNumber + ">> ";
    }

    private static String maybeFormat(String message, Object... args) {
        // If no varargs are supplied, treat it as a request to log the string without formatting.
        return args.length == 0 ? message : String.format(message, args);
    }

}
