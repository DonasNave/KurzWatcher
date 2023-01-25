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
import kotlinx.coroutines.launch
import java.text.DecimalFormat


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val homeViewModel =
            ViewModelProvider(this)[HomeViewModel::class.java]

        val conversionSettings = homeViewModel.conversionSettings
        var lastConversionSettings = homeViewModel.lastConversionSettings


        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.inputConversionValue.text = SpannableStringBuilder(conversionSettings.amount.toInt().toString())

        val inputChoice = binding.choicesInput
        inputChoice.check(CodeToID(conversionSettings.from, true, binding))
        inputChoice.setOnCheckedChangeListener { group, checkedId ->
            conversionSettings.from = IDtoCode(checkedId, binding)
        }

        val outputChoice = binding.choicesOutput
        outputChoice.check(CodeToID(conversionSettings.to, false, binding))
        outputChoice.setOnCheckedChangeListener { group, checkedId ->
            conversionSettings.to = IDtoCode(checkedId, binding)
        }

        val convertButton = binding.conversionButton
        convertButton.setOnClickListener {

            if (binding.inputConversionValue.text != null)
            {
                conversionSettings.amount = binding.inputConversionValue.text.toString().toDouble()

                if (conversionSettings.from == lastConversionSettings.from
                    && conversionSettings.to == lastConversionSettings.to)
                {
                    if (lastConversionSettings.lastResult != null) {
                        binding.outputCurrencyText.text =
                            (lastConversionSettings.lastResult!! / lastConversionSettings.amount
                                    * conversionSettings.amount).toString()
                    }
                }
                else {
                    lifecycleScope.launch {
                        val result = homeViewModel.convert(conversionSettings)
                        if (result.success) {
                            val resultRate = result.info.rate
                            val resultString = DecimalFormat("#,###.##").format(resultRate) + " " + result.query.to
                            binding.outputCurrencyText.text = resultString
                            lastConversionSettings = conversionSettings.copy()
                            lastConversionSettings.lastResult = resultRate
                        } else {
                            binding.outputCurrencyText.text = "Error"
                        }
                    }
                }
            }
        }

        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun CodeToID(code: String, input: Boolean, binding: FragmentHomeBinding) : Int {
        return if (input){
            when(code) {
                "BTC" -> binding.choiceInputBTC.id
                "EUR" -> binding.choiceInputEUR.id
                "USD" -> binding.choiceInputUSD.id
                "GBP" -> binding.choiceInputGBP.id
                "CZK" -> binding.choiceInputCZK.id
                else -> binding.choiceInputBTC.id
            }
        } else {
            when(code) {
                "BTC" -> binding.choiceOutputBtc.id
                "EUR" -> binding.choiceOutputEur.id
                "USD" -> binding.choiceOutputUsd.id
                "GBP" -> binding.choiceOutputGbp.id
                "CZK" -> binding.choiceOutputCzk.id
                else -> binding.choiceOutputBtc.id
            }
        }
    }

    private fun IDtoCode(id: Int, binding: FragmentHomeBinding) : String {
        return when(id) {
            binding.choiceInputBTC.id -> "BTC"
            binding.choiceInputEUR.id -> "EUR"
            binding.choiceInputUSD.id -> "USD"
            binding.choiceInputGBP.id -> "GBP"
            binding.choiceInputCZK.id -> "CZK"
            binding.choiceOutputBtc.id -> "BTC"
            binding.choiceOutputEur.id -> "EUR"
            binding.choiceOutputUsd.id -> "USD"
            binding.choiceOutputGbp.id -> "GBP"
            binding.choiceOutputCzk.id -> "CZK"
            else -> "BTC"
        }
    }
}