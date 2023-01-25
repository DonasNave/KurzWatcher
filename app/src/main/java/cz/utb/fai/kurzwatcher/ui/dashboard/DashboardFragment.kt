package cz.utb.fai.kurzwatcher.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cz.utb.fai.kurzwatcher.R
import cz.utb.fai.kurzwatcher.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment() {

    private val viewModel: DashboardViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        ViewModelProvider(this, DashboardViewModel.Factory(activity.application))[DashboardViewModel::class.java]
    }

    private var viewModelAdapter: KurzAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.kurzList.observe(viewLifecycleOwner) { kurzes ->
            kurzes?.apply {
                viewModelAdapter?.kurzes = kurzes
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val binding: FragmentDashboardBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_dashboard,
            container,
            false)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        viewModelAdapter = KurzAdapter()

        binding.root.findViewById<RecyclerView>(R.id.kurzView).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = viewModelAdapter
        }

        binding.choicesSorting.setOnCheckedChangeListener { _, checkedId ->
            viewModel.onChoiceChanged(when (checkedId) {
                binding.choiceSortLowest.id -> "lowest"
                binding.choiceSortHighest.id -> "highest"
                else -> "latest"
            })
        }

        viewModel.eventNetworkError.observe(viewLifecycleOwner) { isNetworkError ->
            if (isNetworkError) onNetworkError()
        }

        return binding.root
    }

    private fun onNetworkError() {
        if(!viewModel.isNetworkErrorShown.value!!) {
            Toast.makeText(activity, "Network Error", Toast.LENGTH_LONG).show()
            viewModel.onNetworkErrorShown()
        }
    }
}
