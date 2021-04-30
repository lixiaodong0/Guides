package com.lixd.guides.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import com.lixd.guides.dao.entities.Image

/**
 * Created by lixiaodong
 * Created bt date 2021/4/29 10:46 AM
 *
 * @description
 */
@Database(entities = [Image::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun imageDao(): ImageDao
}