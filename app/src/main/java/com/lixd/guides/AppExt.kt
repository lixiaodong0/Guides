package com.lixd.guides

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Rect
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.lixd.guides.dao.AppDatabaseHelper
import com.lixd.guides.dao.entities.Image

/**
 *  Created by lixiaodong
 *  Created bt date 2021/4/28 3:29 PM
 *  @description
 */

/**
 * Android中怎么获得ImageView控件显示的图片宽高
 * https://www.jianshu.com/p/86fa13fe82f8
 */
fun ImageView.getImageDisplayRect(): Rect {
    // 得到imageview中的矩阵，准备得到drawable的拉伸比率
    val m = imageMatrix
    val values = FloatArray(10)
    m.getValues(values)

    // drawable的本身宽高
    val drawable = drawable
    val originalWidth = drawable.intrinsicWidth.toFloat()
    val originalHeight = drawable.intrinsicHeight.toFloat()

    //Image在绘制过程中的变换矩阵，从中获得x和y方向的缩放系数  value[0],[4]
    //得到drawable的实际显示时的宽高
    val displayWidth = (originalWidth * values[0]).toInt()
    val displayHeight = (originalHeight * values[4]).toInt()

    // 计算出对应的Rect
    val rect = Rect()
    rect.left = ((originalWidth - displayWidth) / 2).toInt()
    rect.top = ((originalHeight - displayHeight) / 2).toInt()
    rect.right = rect.left + displayWidth
    rect.bottom = rect.top + displayHeight
    return rect
}

fun getAppDatabase() = AppDatabaseHelper.INSTANCE.getAppDatabase()

fun showToast(context: Context, text: String) {
    Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
}

