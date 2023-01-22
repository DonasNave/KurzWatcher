package cz.utb.fai.kurzwatcher.api

import com.squareup.moshi.JsonClass
import cz.utb.fai.kurzwatcher.database.DatabaseKurz
import cz.utb.fai.kurzwatcher.domain.KurzModel
import java.util.Date

@JsonClass(generateAdapter = true)
data class KurzApiModel(
    val base: String,
    val date: Date,
    val rates: Map<String, Double>,
    val success: Boolean,
    val timestamp: Long
)

@JsonClass(generateAdapter = true)
data class KurzListValue(
    val code: String,
    val inCZK: Double
)

fun KurzApiModel.asDomainModel(): List<KurzModel> {
    return rates.map {
        KurzModel(
            Code = it.key,
            InCZK = 1/it.value)
    }
}

fun KurzApiModel.asDatabaseModel(): List<DatabaseKurz> {
    return rates.map {
        DatabaseKurz(
            code = it.key,
            inCZK = 1/it.value)
    }
}