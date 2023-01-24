package cz.utb.fai.kurzwatcher.ui.home

import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import cz.utb.fai.kurzwatcher.databinding.FragmentHomeBinding
import cz.utb.fai.kurzwatcher.domain.ConversionSettings
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    private var conversionSettings: ConversionSettings = ConversionSettings(
        from = "CZK",
        to = "EUR",
        amount = 1.0
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val homeViewModel =
            ViewModelProvider(this)[HomeViewModel::class.java]

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val inputChoice = binding.choicesInput

        inputChoice.setOnCheckedChangeListener { group, checkedId ->
            conversionSettings.from = when(checkedId) {
                binding.choiceInputBTC.id -> "BTC"
                binding.choiceInputEUR.id -> "EUR"
                binding.choiceInputUSD.id -> "USD"
                binding.choiceInputGBP.id -> "GBP"
                else -> "BTC"
            }
        }

        val outputChoice = binding.choicesOutput
        outputChoice.setOnCheckedChangeListener { group, checkedId ->
            conversionSettings.to = when(checkedId) {
                binding.choiceOutputBtc.id -> "BTC"
                binding.choiceOutputEur.id -> "EUR"
                binding.choiceOutputUsd.id -> "USD"
                binding.choiceOutputGbp.id -> "GBP"
                else -> "BTC"
            }
        }

        val convertButton = binding.conversionButton
        convertButton.setOnClickListener {

            conversionSettings.amount = binding.inputConversionValue.text.toString().toDouble()

            lifecycleScope.launch {
                val result = homeViewModel.convert(conversionSettings)
                if (result.success) {
                    binding.outputCurrencyText.text = SpannableStringBuilder.valueOf(result.info.rate.toString())
                } else {
                    binding.outputCurrencyText.text = SpannableStringBuilder.valueOf("Error")
                }
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}