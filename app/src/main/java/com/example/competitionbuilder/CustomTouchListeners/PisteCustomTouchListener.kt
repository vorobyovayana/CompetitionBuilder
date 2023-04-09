import android.content.Intent
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import com.example.competitionbuilder.CustomViews.PisteView
import com.example.competitionbuilder.VenuePlan.PopUpWindow

// Custom Touch listener for pisteView
open class PisteCustomTouchListener(private val pisteView: PisteView): View.OnTouchListener {

    private var initialX: Float = 0.toFloat()
    private var initialY: Float = 0.toFloat()
    private var lastX: Float = 0.toFloat()
    private var lastY: Float = 0.toFloat()


    private val gestureDetector = GestureDetector(pisteView.context, object : GestureDetector.SimpleOnGestureListener() {
        // Detect LongPress
        override fun onLongPress(e: MotionEvent) {
            onLongClick()
            return super.onLongPress(e)
        }

        // Detect DoubleTap
        override fun onDoubleTap(e: MotionEvent): Boolean {
            onDoubleClick()
            return super.onDoubleTap(e)
        }
    })

    // I can override these methods as I need in the activity
    open fun onDoubleClick(){
        Log.d(
            "GESTURE_DETECTOR",
            "Detected double click in the custom touch listener"
        )
    }

    open fun onLongClick(){
        Log.d(
            "GESTURE_DETECTOR",
            "Detected long click in the custom touch listener"
        )
    }


    // Overriding onTouch to enable moving the view.
    // I cannot do it from the activity, so I am implementing it here
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

            else -> return false
        }
    }
}
