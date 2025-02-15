package ensa.ma.sensors.ui.share

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import ensa.ma.sensors.R

class ShareFragment : Fragment() {
    private var shareViewModel: ShareViewModel? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        shareViewModel = ViewModelProviders.of(this).get(ShareViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_share, container, false)
        val textView = root.findViewById<TextView>(R.id.text_share)
        shareViewModel!!.text.observe(this) { s -> textView.text = s }
        return root
    }
}