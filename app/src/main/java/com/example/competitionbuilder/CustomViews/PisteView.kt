package com.example.competitionbuilder.CustomViews

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View

class PisteView(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    private var width: Float = 0F
    private var height: Float = 0F
    private var aspectRatio: Float = 0f

    fun setAspectRatio(aspectRatio: Float) {
        this.aspectRatio = aspectRatio
        invalidate()
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val viewWidth = measuredWidth
        val viewHeight = measuredHeight

        setMeasuredDimension(viewWidth, viewHeight)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val paint = Paint().apply {
            color = Color.GREEN
            style = Paint.Style.STROKE
            strokeWidth = 8f
        }

        // Calculate the aspect ratio of the rectangle
        val pisteAspectRatio = width.toFloat() / height.toFloat()

        // Calculate the aspect ratio of the view
        // val viewAspectRatio = canvas.width.toFloat() / canvas.height.toFloat()
        val rectAspectRatio = this.aspectRatio

        // Scale the dimensions of the piste to fill the rectangle while preserving its aspect ratio
        val pisteWidth: Int
        val pisteHeight: Int
        if (rectAspectRatio > pisteAspectRatio) {
            pisteWidth = (canvas.height * rectAspectRatio).toInt()
            pisteHeight = canvas.height
        } else {
            pisteWidth = canvas.width
            pisteHeight = (canvas.width / rectAspectRatio).toInt()
        }

//        // Calculate the position of the rectangle in the view
//        val rectLeft = (canvas.width - rectWidth) / 2f
//        val rectTop = (canvas.height - rectHeight) / 2f
//        val rectRight = rectLeft + rectWidth
//        val rectBottom = rectTop + rectHeight

        // Calculate the position of the rectangle in the view
        val pisteLeft = (canvas.width - pisteWidth) / 2f
        val pisteTop = (canvas.height - pisteHeight) / 2f
        val pisteRight = pisteLeft + pisteTop
        val pisteBottom = pisteTop + pisteHeight

        val piste = RectF(pisteLeft, pisteTop, pisteRight, pisteBottom)
        canvas.drawRect(piste, paint)
    }

    fun setDimensions(width: Float, height: Float) {
        this.width = width
        this.height = height
        invalidate()
    }
}