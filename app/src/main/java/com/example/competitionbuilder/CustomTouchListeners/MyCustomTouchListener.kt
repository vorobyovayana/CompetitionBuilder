import android.content.Intent
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import com.example.competitionbuilder.CustomViews.PisteView
import com.example.competitionbuilder.VenuePlan.PopUpWindow

open class MyCustomTouchListener(private val pisteView: PisteView): View.OnTouchListener {

    private var initialX: Float = 0.toFloat()
    private var initialY: Float = 0.toFloat()
    private var lastX: Float = 0.toFloat()
    private var lastY: Float = 0.toFloat()
    private val DOUBLE_TAP_TIMEOUT: Long = 1000 // milliseconds
    private var lastTapTime: Long = 0

    private val gestureDetector = GestureDetector(pisteView.context, object : GestureDetector.SimpleOnGestureListener() {
        override fun onLongPress(e: MotionEvent) {
            onLongClick()
            return super.onLongPress(e)
        }

        override fun onDoubleTap(e: MotionEvent): Boolean {
            onDoubleClick()
            return super.onDoubleTap(e)
        }
    })

    open fun onDoubleClick(){
        Log.d(
            "GESTUREDEMO",
            "Detected double click in the custom touch listener"
        )
    }

    open fun onLongClick(){
        Log.d(
            "GESTUREDEMO",
            "Detected long click in the custom touch listener"
        )
    }


    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        if (event != null) {
            gestureDetector.onTouchEvent(event)
        }
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                initialX = pisteView.x
                initialY = pisteView.y
                lastX = event.rawX
                lastY = event.rawY
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                val deltaX: Float = event.rawX - lastX
                val deltaY: Float = event.rawY - lastY
                pisteView.x = initialX + deltaX
                pisteView.y = initialY + deltaY
                return true
            }
            MotionEvent.ACTION_UP -> {

                return true
            }
            MotionEvent.ACTION_CANCEL -> {
                return true
            }
            MotionEvent.ACTION_OUTSIDE -> {
                return true
            }
            MotionEvent.ACTION_POINTER_DOWN -> {
                return true
            }
            MotionEvent.ACTION_POINTER_UP -> {
                return true
            }
            MotionEvent.ACTION_SCROLL -> {
                return true
            }

            else -> return false
        }
    }
}
