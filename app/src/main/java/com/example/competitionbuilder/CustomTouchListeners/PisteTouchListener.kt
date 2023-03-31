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
            }

            MotionEvent.ACTION_MOVE -> {
                view.animate()
                    .x(x + dX)
                    .y(y + dY)
                    .setDuration(0)
                    .start()
            }

            else -> return false
        }
        return true
    }



}
