package cz.utb.fai.kurzwatcher.domain

import java.text.DateFormat
import java.text.DecimalFormat

data class KurzModel(val Name: String,
                val InDollars: DecimalFormat,
                val Code: String) {

}

data class TargetValueModel(val OldCurCode: String,
                     val TargetCurCode: String,
                     val Value: DecimalFormat,
                     val CreatedTime: DateFormat) {

}