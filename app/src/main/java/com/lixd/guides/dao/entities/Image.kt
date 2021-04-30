package com.lixd.guides.dao.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

/**
 * Created by lixiaodong
 * Created bt date 2021/4/29 10:26 AM
 *
 * @description
 */
@Entity
data class Image(@PrimaryKey var path: String, var source: Int = LOCAL_IMAGE, var date: Long = 0L) :
    Serializable {
    /*
     * 图片来源
     * 0:手机图片
     * 1:网络图片
     */
//    var source = LOCAL_IMAGE

    /*
     * 图片日期
     */
//    var date = 0L

    companion object {
        const val LOCAL_IMAGE = 0
        const val NETWORK_IMAGE = 1
    }
}