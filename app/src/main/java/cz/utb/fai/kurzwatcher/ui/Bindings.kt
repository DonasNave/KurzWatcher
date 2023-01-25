package cz.utb.fai.kurzwatcher.ui

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.text.DecimalFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@BindingAdapter("android:text")
fun setText(view: TextView, state: LocalDate) {
    view.text = DateTimeFormatter.ofPattern("dd/MM/YYYY").format(state)
}

@BindingAdapter("android:text")
fun setText(view: TextView, state: Double) {
    view.text = DecimalFormat("#,###.###").format(state)
}

@BindingAdapter("isNetworkError", "kurzList")
fun hideIfNetworkError(view: View, isNetWorkError: Boolean, kurzList: Any?) {
    view.visibility = if (kurzList != null) View.GONE else View.VISIBLE

    if(isNetWorkError) {
        view.visibility = View.GONE
    }
}