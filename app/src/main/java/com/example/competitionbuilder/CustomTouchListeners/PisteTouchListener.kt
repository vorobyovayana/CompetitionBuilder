package com.example.competitionbuilder.CustomTouchListeners

import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.example.competitionbuilder.CustomViews.PisteView

class PisteTouchListener : View.OnTouchListener {
    var dX = 0f
    var dY = 0f
    var position = true

    @JvmName("setPosition1")
    fun setPosition(p: Boolean){
        this.position = p
    }

    override fun onTouch(view: View, event: MotionEvent): Boolean {
        val x = event.rawX
        val y = event.rawY

        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                dX = view.x - x
                dY = view.y - y
                Log.d("StartX", dX.toString())
                Log.d("StartY", dY.toString())
                Log.d("Move", "Move on Piste  is detected")
            }

            MotionEvent.ACTION_MOVE -> {
                view.animate()
                    .x(x + dX)
                    .y(y + dY)
                    .setDuration(0)
                    .start()
                Log.d("EndX", dX.toString())
                Log.d("EndY", dY.toString())
            }

            else -> return false
        }
        return true
    }



}
