package com.lixd.guides.ui.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import com.github.chrisbanes.photoview.PhotoView
import com.lixd.guides.getImageDisplayRect


/**
 *  Created by lixiaodong
 *  Created bt date 2021/4/25 3:22 PM
 *  @description
 */
class GuidesView constructor(context: Context, attrs: AttributeSet) :
    PhotoView(context, attrs) {

    val TAG = "GuidesView"

    //辅助线格式
    var guidesLines = 3
        set(value) {
            field = value
            invalidate()
        }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawGuidesLines(canvas, guidesLines)
    }

    private fun drawGuidesLines(canvas: Canvas, count: Int) {
        drawable?.apply {
            val imageDisplayRect = getImageDisplayRect()
            Log.d(
                TAG,
                "width:${width},height:${height},intrinsicWidth:${intrinsicWidth},intrinsicHeight:${intrinsicHeight}" +
                        ",imageDisplayRect:${imageDisplayRect}"
            )
            val drawRectF = RectF()
            drawRectF.left = 0f
            drawRectF.top = 0f
            drawRectF.right = imageDisplayRect.width().toFloat()
            drawRectF.bottom = imageDisplayRect.height().toFloat()
            val path = Path()
            path.addRect(drawRectF, Path.Direction.CW)

            val left = if (drawRectF.right < width) (width / 2 - drawRectF.right / 2) else 0f
            val top = height / 2 - drawRectF.bottom / 2
            val saveCount = canvas.saveCount
            canvas.save()
            canvas.translate(left, top)

            val wCount = drawRectF.width() / count
            val hCount = drawRectF.height() / count
            for (i in 1 until count) {
                path.moveTo(0f, hCount * i)
                path.lineTo(drawRectF.right, hCount * i)

                path.moveTo(wCount * i, 0f)
                path.lineTo(wCount * i, drawRectF.bottom)
            }

            val paint = Paint(Paint.ANTI_ALIAS_FLAG)
            paint.color = Color.BLUE
            paint.strokeWidth = 10f
            paint.style = Paint.Style.STROKE

            canvas.drawPath(path, paint)
            canvas.restoreToCount(saveCount)
        }
    }
}