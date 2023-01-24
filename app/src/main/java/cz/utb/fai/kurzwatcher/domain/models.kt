package cz.utb.fai.kurzwatcher.domain

import cz.utb.fai.kurzwatcher.api.Info
import cz.utb.fai.kurzwatcher.api.Query
import java.time.LocalDate
data class KurzEntryModel(val Code: String,
                         val CreatedTime: LocalDate,
                         val Rate: Double)

data class ConversionResultModel(val date: String,
                           val info: Info,
                           val query: Query,
                           val result: Double,
                           val success: Boolean)

data class ConversionSettings(
    var from: String,
    var to: String,
    var amount: Double)


