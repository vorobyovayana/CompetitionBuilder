package com.example.competitionbuilder.CustomViews

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import android.util.Log
import android.view.DragEvent
import android.view.MotionEvent
import android.view.View
import com.example.competitionbuilder.CustomTouchListeners.CustomTouchListener

class PisteView(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    private var width: Float = 0F
    private var height: Float = 0F
    private var oneMeter: Float = 0F
    private var pisteWidth: Int  = 0
    private var pisteHeight: Int  = 0
    fun setOneMeter(oneM: Float){
        this.oneMeter = oneM
        invalidate()
    }

    fun getPisteWidth(): Int{
        return this.pisteWidth
    }
    fun getPisteHeight(): Int{
        return this.pisteHeight
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val paint = Paint().apply {
            color = Color.parseColor("#7900FF")
            style = Paint.Style.STROKE
            strokeWidth = 8f
        }

        val dashPaint = Paint().apply {
            color = Color.parseColor("#36454F")
            style = Paint.Style.STROKE
            strokeWidth = 4f
            pathEffect = android.graphics.DashPathEffect(floatArrayOf(20f, 20f), 0f)
        }

        // Get actual rectWidth and rectHeight (in pixels) from rectangle
        // Divide the width in pixels to the width entered by user - to get 1 meter
        // Multiply 17f and number in pixels for one meter to get the actual width in pixels
        // Do the same for height
        // Create the if logic like above to include the different venue shapes
        val viewAspectRatio = canvas.width.toFloat() / canvas.height.toFloat()
        val pisteAspectRatio = width / height


        if (viewAspectRatio > pisteAspectRatio) {
            pisteWidth = (this.width * this.oneMeter).toInt()
            pisteHeight = (this.height * this.oneMeter).toInt()
        } else {
            pisteWidth = (this.width * this.oneMeter).toInt()
            pisteHeight = (this.height * this.oneMeter).toInt()
        }

        // Calculate the position of the rectangle in the view
        val rectLeft = (canvas.width - pisteWidth) / 2f
        val rectTop = (canvas.height - pisteHeight) / 2f
        val rectRight = rectLeft + pisteWidth
        val rectBottom = rectTop + pisteHeight

        // Draw the piste itself
        val rect = RectF(rectLeft, rectTop, rectRight, rectBottom)
        canvas.drawRect(rect, paint)

        // Draw the dash line around the piste to signify the space that cannot be occupied because of the equipment
        val dashRect = RectF(rectLeft - 20f, rectTop - 20f, rectRight + 20f, rectBottom + 20f)
        canvas.drawRect(dashRect, dashPaint)

        Log.d("OnDraw", pisteHeight.toString())
        Log.d("OnDraw", pisteWidth.toString())
    }

    fun setDimensions(width: Float, height: Float) {
        this.width = width
        this.height = height
        invalidate()
    }

}
