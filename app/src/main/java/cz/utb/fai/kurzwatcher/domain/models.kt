package cz.utb.fai.kurzwatcher.domain

import java.util.Date

data class KurzModel(val InCZK: Double,
                     val Code: String)

data class TargetValueModel(val OldCurCode: String,
                     val TargetCurCode: String,
                     val ValueInCZK: Double,
                     val CreatedTime: Date)