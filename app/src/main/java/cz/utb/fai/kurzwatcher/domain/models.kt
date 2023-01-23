package cz.utb.fai.kurzwatcher.domain

import java.util.Date

data class KurzModel(val Base: String,
                     val CreatedTime: Date,
                     val Rates: Map<String, Double>,
                     val Success: Boolean,
                     val Timestamp: Long)

