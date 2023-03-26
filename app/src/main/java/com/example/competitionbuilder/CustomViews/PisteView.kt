package com.example.competitionbuilder.CustomViews

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.util.Log
import android.view.View
class PisteView(context: Context, attrs: AttributeSet?) : View(context, attrs) {
    private var aspectRatio: Float = 0f

    private var width: Float = 0F
    private var height: Float = 0F

    fun setAspectRatio(aspectRatio: Float) {
        this.aspectRatio = aspectRatio
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val paint = Paint().apply {
            color = Color.GREEN
            style = Paint.Style.STROKE
            strokeWidth = 8f
        }

        // Calculate the aspect ratio of the view
        val viewAspectRatio = canvas.width.toFloat() / canvas.height.toFloat()
        val pisteAspectRatio = width/height
        // Scale the dimensions of the rectangle to fill the view while preserving its aspect ratio
        val rectWidth: Int
        val rectHeight: Int
        if (viewAspectRatio > pisteAspectRatio) {
            rectWidth = (canvas.height * pisteAspectRatio).toInt()
            rectHeight = canvas.height
        } else {
            rectWidth = canvas.width
            rectHeight = (canvas.width / pisteAspectRatio).toInt()
        }

        // Calculate the position of the rectangle in the view
        val rectLeft = (canvas.width - rectWidth) / 2f
        val rectTop = (canvas.height - rectHeight) / 2f
        val rectRight = rectLeft + rectWidth
        val rectBottom = rectTop + rectHeight

        val rect = RectF(rectLeft, rectTop, rectRight, rectBottom)
        canvas.drawRect(rect, paint)


    }
    fun setDimensions(width: Float, height: Float) {
        this.width = width
        this.height = height
        invalidate()
    }
}
