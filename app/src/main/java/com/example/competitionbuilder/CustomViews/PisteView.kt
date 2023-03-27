package com.example.competitionbuilder.CustomViews

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
class PisteView(context: Context, attrs: AttributeSet?) : View(context, attrs) {
    private var aspectRatio: Float = 0f

    private var width: Float = 0F
    private var height: Float = 0F
    private var oneMeter: Int = 0

    private var rectWidth: Int = 0
    private var rectHeight: Int = 0




    private var lastX: Float = 0f
    private var lastY: Float = 0f
    private var offsetX: Float = 0f
    private var offsetY: Float = 0f

    fun setAspectRatio(aspectRatio: Float) {
        this.aspectRatio = aspectRatio
        invalidate()
    }

    fun setOneMeter(oneM: Int){
        this.oneMeter = oneM
        invalidate()
    }

    fun setRectWidth(rectW: Int){
        this.rectWidth = rectW
    }

    fun setRectHeight(rectH: Int){
        this.rectHeight = rectH
    }



    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val paint = Paint().apply {
            color = Color.GREEN
            style = Paint.Style.STROKE
            strokeWidth = 8f
        }

        // Calculate the aspect ratio of the view
//        val viewAspectRatio = canvas.width.toFloat() / canvas.height.toFloat()
//        val pisteAspectRatio = width/height
//        // Scale the dimensions of the piste to fill the view while preserving its aspect ratio
//        val pisteWidth: Int
//        val pisteHeight: Int
//        if (viewAspectRatio > pisteAspectRatio) {
//            pisteWidth = (canvas.height * pisteAspectRatio).toInt()
//            pisteHeight = canvas.height
//        } else {
//            pisteWidth = canvas.width
//            pisteHeight = (canvas.width / pisteAspectRatio).toInt()
//        }

        // What if: get actual rectWidth and rectHeight (in pixels) from rectangle
        // Divide the width in pixels to the width entered by user - to get 1 meter
        // Multiply 17f and number in pixels for one meter to get the actual width in pixels
        // Do the same for height
        // Create the if logic like above to include the different venue shapes


        val viewAspectRatio = canvas.width.toFloat() / canvas.height.toFloat()
        val pisteAspectRatio = width/height
        // Scale the dimensions of the rectangle to fill the view while preserving its aspect ratio
        val pisteWidth: Int
        val pisteHeight: Int
        if (viewAspectRatio > pisteAspectRatio) {
            //pisteWidth = (canvas.height * pisteAspectRatio).toInt()
            //pisteWidth = (953*pisteAspectRatio).toInt()
            pisteWidth = (this.rectHeight * pisteAspectRatio).toInt()
            Log.d("pisteWidth", pisteWidth.toString())
            // D/pisteWidth: 145

            pisteHeight = this.rectHeight
            Log.d("pisteHeight",pisteHeight.toString())
            //D/pisteHeight: 822
        } else {
            //pisteWidth = canvas.width
            pisteWidth = this.rectWidth
            // D/pisteWidth: 145

            pisteHeight = (this.rectWidth/ pisteAspectRatio).toInt()
            //D/pisteHeight: 822
        }

        // Calculate the position of the rectangle in the view
        val rectLeft = (this.rectWidth - pisteWidth) / 2f
        val rectTop = (this.rectHeight - pisteHeight) / 2f
        val rectRight = rectLeft + pisteWidth
        val rectBottom = rectTop + pisteHeight
        val diff = rectRight-rectLeft
        Log.d("LEN", "Difference between left and right corner is: $diff ")

        val rect = RectF(rectLeft, rectTop, rectRight, rectBottom)
        canvas.drawRect(rect, paint)


    }
    fun setDimensions(width: Float, height: Float) {
        this.width = width
        this.height = height

        invalidate()
    }

    fun onTouch(view: View, event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                lastX = event.rawX
                lastY = event.rawY
                offsetX = x - event.rawX
                offsetY = y - event.rawY
            }
            MotionEvent.ACTION_MOVE -> {
                val newX = event.rawX + offsetX
                val newY = event.rawY + offsetY
                x = newX
                y = newY
            }
        }
        return true
    }

}
