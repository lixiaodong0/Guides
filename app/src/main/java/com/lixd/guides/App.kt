package com.lixd.guides

import android.app.Application
import androidx.room.Room
import coil.Coil
import coil.ImageLoader
import coil.decode.GifDecoder
import coil.imageLoader
import com.lixd.guides.dao.AppDatabase
import com.lixd.guides.dao.AppDatabaseHelper

/**
 * Created by lixiaodong
 * Created bt date 2021/4/29 10:51 AM
 *
 * @description
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        AppDatabaseHelper.INSTANCE.init(this)
    }
}