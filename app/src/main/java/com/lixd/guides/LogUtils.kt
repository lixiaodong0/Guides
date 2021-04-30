package com.lixd.guides

import android.util.Log

/**
 * Created by lixiaodong
 * Created bt date 2021/4/30 2:47 PM
 *
 * @description
 */
class LogUtils {

    companion object {
        private const val TAG = "guides"

        fun d(any: Any?) {
            any.let {
                Log.d(TAG, it.toString())
            }
        }

        fun e(any: Any?) {
            any.let {
                Log.e(TAG, it.toString())
            }
        }
    }
}