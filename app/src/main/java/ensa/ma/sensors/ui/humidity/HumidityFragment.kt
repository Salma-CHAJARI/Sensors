package ensa.ma.sensors.ui.humidity

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import ensa.ma.sensors.R
import java.util.Date

/**
 * A simple [Fragment] subclass.
 */
class HumidityFragment : Fragment(), SensorEventListener {
    private var chart: LineChart? = null
    private var mSensorManager: SensorManager? = null
    private var mHumidSensor: Sensor? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mSensorManager = activity!!.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        mHumidSensor = mSensorManager!!.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY)
        if (mHumidSensor == null) {
            Toast.makeText(context, R.string.message_neg, Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_humidity, container, false)
        chart = root.findViewById<View>(R.id.chart) as LineChart
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun addEntry(event: SensorEvent) {
        val d = Date()
        entries.add(Entry(entries.size.toFloat(), event.values[0]))
        val dataSet = LineDataSet(entries, "Humidity - Time series")
        val data = LineData(dataSet)
        Log.d("size", entries.size.toString() + "")
        val xAxis = chart!!.xAxis
        chart!!.setData(data)
        chart!!.notifyDataSetChanged()
        //refresh
        chart!!.invalidate()
    }

    override fun onResume() {
        super.onResume()
        mSensorManager!!.registerListener(this, mHumidSensor, SensorManager.SENSOR_DELAY_NORMAL)
        entries.clear()
    }

    override fun onPause() {
        super.onPause()
        mSensorManager!!.unregisterListener(this)
        entries.clear()
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}
    override fun onSensorChanged(event: SensorEvent) {
        addEntry(event)
    }

    companion object {
        var entries = ArrayList<Entry>()
    }
}
