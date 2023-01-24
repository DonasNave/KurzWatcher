package cz.utb.fai.kurzwatcher.ui.dashboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import cz.utb.fai.kurzwatcher.R
import cz.utb.fai.kurzwatcher.databinding.KurzItemBinding
import cz.utb.fai.kurzwatcher.domain.KurzEntryModel
import java.time.LocalDate


class KurzAdapter() : RecyclerView.Adapter<DashboardViewHolder>() {

    var kurzes: List<KurzEntryModel> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardViewHolder {
        val withDataBinding: KurzItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            DashboardViewHolder.LAYOUT,
            parent,
            false)
        return DashboardViewHolder(withDataBinding)
    }

    override fun getItemCount() = kurzes.size

    override fun onBindViewHolder(holder: DashboardViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.kurz = kurzes[position]
        }
    }

}

class DashboardViewHolder(val viewDataBinding: KurzItemBinding) :
    RecyclerView.ViewHolder(viewDataBinding.root) {
    companion object {
        @LayoutRes
        val LAYOUT = R.layout.kurz_item
    }
}