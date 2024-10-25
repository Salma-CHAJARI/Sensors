package ensa.ma.sensors.ui.sensors

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ensa.ma.sensors.R
import ensa.ma.sensors.beans.SensorItem
import ensa.ma.sensors.ui.sensors.ListSonsorsFragment.OnListFragmentInteractionListener

class ListSonsorsFragmentRecyclerViewAdapter(
    private val mValues: List<SensorItem>,
    private val mListener: OnListFragmentInteractionListener?
) : RecyclerView.Adapter<ListSonsorsFragmentRecyclerViewAdapter.ViewHolder?>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_listsonsors, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mItem = mValues[position]
        holder.mIdView.text = mValues[position].id
        holder.mNameView.text = mValues[position].name
        holder.mVersionView.text = mValues[position].version
        holder.mVendorView.text = mValues[position].vendor
        holder.mTypeView.text = mValues[position].type
        holder.mView.setOnClickListener { mListener?.onListFragmentInteraction(holder.mItem) }
    }

    override fun getItemCount(): Int {
        return mValues.size
    }

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mIdView: TextView
        val mNameView: TextView
        val mTypeView: TextView
        val mVendorView: TextView
        val mVersionView: TextView
        var mItem: SensorItem? = null

        init {
            mIdView = mView.findViewById<View>(R.id.item_number) as TextView
            mNameView = mView.findViewById<View>(R.id.name) as TextView
            mTypeView = mView.findViewById<View>(R.id.type) as TextView
            mVendorView = mView.findViewById<View>(R.id.vendor) as TextView
            mVersionView = mView.findViewById<View>(R.id.version) as TextView
        }

        override fun toString(): String {
            return super.toString() + " '" + mNameView.getText() + "'"
        }
    }
}
