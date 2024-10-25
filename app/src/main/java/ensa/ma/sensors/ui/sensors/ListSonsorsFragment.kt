package ensa.ma.sensors.ui.sensors

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ensa.ma.sensors.R
import ensa.ma.sensors.beans.SensorItem

class ListSonsorsFragment : Fragment() {
    private var mColumnCount = 1
    private var mListener: OnListFragmentInteractionListener? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mColumnCount = arguments!!.getInt(ARG_COLUMN_COUNT)
        }
    }

    fun loadSensor(): List<SensorItem> {
        val sensors: MutableList<SensorItem> = ArrayList()
        val sensorManager =
            this.activity!!.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val sListe = sensorManager.getSensorList(Sensor.TYPE_ALL)
        var id = 0
        for (s in sListe) {
            id++
            sensors.add(
                SensorItem(
                    id.toString() + "",
                    s.name,
                    sensorTypeToString(s.type),
                    s.vendor,
                    s.version.toString() + ""
                )
            )
        }
        return sensors
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_listsonsors_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            val context = view.getContext()
            val recyclerView = view
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(LinearLayoutManager(context))
            } else {
                recyclerView.setLayoutManager(GridLayoutManager(context, mColumnCount))
            }
            recyclerView.setAdapter(ListSonsorsFragmentRecyclerViewAdapter(loadSensor(), mListener))
        }
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnListFragmentInteractionListener) {
            mListener = context
        } else {
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    interface OnListFragmentInteractionListener {
        fun onListFragmentInteraction(item: SensorItem?)
    }

    companion object {
        private const val ARG_COLUMN_COUNT = "column-count"
        @Suppress("unused")
        fun newInstance(columnCount: Int): ListSonsorsFragment {
            val fragment = ListSonsorsFragment()
            val args = Bundle()
            args.putInt(ARG_COLUMN_COUNT, columnCount)
            fragment.setArguments(args)
            return fragment
        }

        fun sensorTypeToString(sensorType: Int): String {
            return when (sensorType) {
                Sensor.TYPE_ACCELEROMETER -> "Accelerometer"
                Sensor.TYPE_AMBIENT_TEMPERATURE, Sensor.TYPE_TEMPERATURE -> "Ambient Temperature"
                Sensor.TYPE_GAME_ROTATION_VECTOR -> "Game Rotation Vector"
                Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR -> "Geomagnetic Rotation Vector"
                Sensor.TYPE_GRAVITY -> "Gravity"
                Sensor.TYPE_GYROSCOPE -> "Gyroscope"
                Sensor.TYPE_GYROSCOPE_UNCALIBRATED -> "Gyroscope Uncalibrated"
                Sensor.TYPE_HEART_RATE -> "Heart Rate Monitor"
                Sensor.TYPE_LIGHT -> "Light"
                Sensor.TYPE_LINEAR_ACCELERATION -> "Linear Acceleration"
                Sensor.TYPE_MAGNETIC_FIELD -> "Magnetic Field"
                Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED -> "Magnetic Field Uncalibrated"
                Sensor.TYPE_ORIENTATION -> "Orientation"
                Sensor.TYPE_PRESSURE -> "Orientation"
                Sensor.TYPE_PROXIMITY -> "Proximity"
                Sensor.TYPE_RELATIVE_HUMIDITY -> "Relative Humidity"
                Sensor.TYPE_ROTATION_VECTOR -> "Rotation Vector"
                Sensor.TYPE_SIGNIFICANT_MOTION -> "Significant Motion"
                Sensor.TYPE_STEP_COUNTER -> "Step Counter"
                Sensor.TYPE_STEP_DETECTOR -> "Step Detector"
                else -> "Unknown"
            }
        }
    }
}
