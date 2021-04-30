package com.lixd.guides.dao

import android.content.Context
import androidx.room.Room
import com.lixd.guides.APP_DATA_BASE_NAME
import com.lixd.guides.App

/**
 * Created by lixiaodong
 * Created bt date 2021/4/29 10:56 AM
 *
 * @description
 */
class AppDatabaseHelper {
    private lateinit var context: Context

    private lateinit var db: AppDatabase

    /**
     * 初始化数据库
     */
    fun init(context: Context) {
        this.context = context
        db = Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            APP_DATA_BASE_NAME
        )
            .allowMainThreadQueries() //允许在主线程中查询
            .build()
    }

    /**
     * 对外提供访问数据库的方法
     */
    fun getAppDatabase() = db

    companion object Holder {
        val INSTANCE = AppDatabaseHelper()
    }
}