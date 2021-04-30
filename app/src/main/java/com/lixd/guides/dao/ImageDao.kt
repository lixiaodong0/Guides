package com.lixd.guides.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lixd.guides.dao.entities.Image
import kotlinx.coroutines.flow.Flow

/**
 *  Created by lixiaodong
 *  Created bt date 2021/4/29 10:30 AM
 *  @description
 */
@Dao
interface ImageDao {

    /**
     * 分页查询
     * @param page 页码
     * @param pageSize 页码大小
     */
    @Query("SELECT * FROM image ORDER BY date desc LIMIT (:page * :pageSize), (:pageSize)")
    fun pagingQuery(page: Int, pageSize: Int): List<Image>

    /**
     * 插入
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(image: Image)
}