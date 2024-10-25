package ensa.ma.sensors.ui.magnetic

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
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import ensa.ma.sensors.R
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Date
import java.util.Locale
import kotlin.math.sqrt

/**
 * A simple [Fragment] subclass.
 */
class MagneticFragment : Fragment(), SensorEventListener {
    private var mSensorManager: SensorManager? = null
    private var mMagneticSensor: Sensor? = null
    private var chart: LineChart? = null
    private var value: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mSensorManager = activity!!.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        mMagneticSensor = mSensorManager!!.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)
        if (mMagneticSensor == null) {
            Toast.makeText(context, R.string.message_neg, Toast.LENGTH_LONG).show()
        }
    }

    private fun addEntry(value: Double) {
        val d = Date()
        entries.add(Entry(entries.size.toFloat(), value.toFloat()))
        val dataSet = LineDataSet(entries, "Magnetic - Time series")
        val data = LineData(dataSet)
        Log.d("size", entries.size.toString() + "")
        val xAxis = chart!!.xAxis
        chart!!.setData(data)
        chart!!.notifyDataSetChanged()
        // chart.animateX(900000000);
        //refresh
        chart!!.invalidate()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_magnetic, container, false)
        value = root.findViewById<View>(R.id.value) as TextView
        chart = root.findViewById<View>(R.id.chart) as LineChart
        // define decimal formatter
        val symbols = DecimalFormatSymbols(Locale.US)
        symbols.setDecimalSeparator('.')
        DECIMAL_FORMATTER = DecimalFormat("#.000", symbols)
        return root
    }

    override fun onResume() {
        super.onResume()
        mSensorManager!!.registerListener(this, mMagneticSensor, SensorManager.SENSOR_DELAY_NORMAL)
        entries.clear()
    }

    override fun onPause() {
        super.onPause()
        mSensorManager!!.unregisterListener(this)
        entries.clear()
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}
    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor.type == Sensor.TYPE_MAGNETIC_FIELD) {
            val magX = event.values[0]
            val magY = event.values[1]
            val magZ = event.values[2]
            val magnitude = sqrt((magX * magX + magY * magY + magZ * magZ).toDouble())
            // set value on the screen
            value!!.text = DECIMAL_FORMATTER!!.format(magnitude) + " \u00B5Tesla"
            addEntry(magnitude)
        }
    }

    companion object {
        var DECIMAL_FORMATTER: DecimalFormat? = null
        var entries = ArrayList<Entry>()
    }
}
