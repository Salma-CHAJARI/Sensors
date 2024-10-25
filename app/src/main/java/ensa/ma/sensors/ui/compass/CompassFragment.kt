package ensa.ma.sensors.ui.compass

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import ensa.ma.sensors.R

/**
 * A simple [Fragment] subclass.
 */
class CompassFragment : Fragment(), SensorEventListener {
    // define the display assembly compass picture
    private var image: ImageView? = null

    // record the compass picture angle turned
    private val currentDegree = 0f

    // device sensor manager
    private var mSensorManager: SensorManager? = null
    private var mCompassSensor: Sensor? = null
    var tvHeading: TextView? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_compass, container, false)
        image = root.findViewById<View>(R.id.imageViewCompass) as ImageView

        // TextView that will tell the user what degree is he heading
        tvHeading = root.findViewById<View>(R.id.tvHeading) as TextView
        return root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // for the system's orientation sensor registered listeners
        // mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
        //       SensorManager.SENSOR_DELAY_GAME);
        mSensorManager = activity!!.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        mCompassSensor = mSensorManager!!.getDefaultSensor(Sensor.TYPE_ORIENTATION)
        if (mCompassSensor == null) {
            Toast.makeText(context, R.string.message_neg, Toast.LENGTH_LONG).show()
        }
    }

    override fun onResume() {
        super.onResume()
        mSensorManager!!.registerListener(this, mCompassSensor, SensorManager.SENSOR_DELAY_GAME)
    }

    override fun onSensorChanged(event: SensorEvent) {
        // get the angle around the z-axis rotated
        val degree = Math.round(event.values[0]).toFloat()
        tvHeading!!.text = "Heading: $degree degrees"

        // create a rotation animation (reverse turn degree degrees)
        val ra = RotateAnimation(
            currentDegree,
            -degree,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f
        )

        // how long the animation will take place
        ra.setDuration(210)

        // set the animation after the end of the reservation status
        ra.fillAfter = true

        // Start the animation
        image!!.startAnimation(ra)

        //  image.animate().rotation(360f).setDuration(2000);
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}
}
