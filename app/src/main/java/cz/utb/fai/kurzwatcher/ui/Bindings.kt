package cz.utb.fai.kurzwatcher.ui

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.time.LocalDate

@BindingAdapter("android:text")
fun setText(view: TextView, state: LocalDate) {
    view.text = state.toString()
}

@BindingAdapter("android:text")
fun setText(view: TextView, state: Double) {
    view.text = state.toString()
}

@BindingAdapter("isNetworkError", "kurzList")
fun hideIfNetworkError(view: View, isNetWorkError: Boolean, kurzList: Any?) {
    view.visibility = if (kurzList != null) View.GONE else View.VISIBLE

    if(isNetWorkError) {
        view.visibility = View.GONE
    }
}