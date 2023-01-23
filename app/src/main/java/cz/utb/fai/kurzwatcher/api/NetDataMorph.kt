package cz.utb.fai.kurzwatcher.api

import com.squareup.moshi.JsonClass
import cz.utb.fai.kurzwatcher.database.DatabaseKurz
import cz.utb.fai.kurzwatcher.domain.KurzModel
import java.util.Date

@JsonClass(generateAdapter = true)
data class KurzApiModel(
    val base: String,
    val createdTime: Date,
    val rates: Map<String, Double>,
    val success: Boolean,
    val timestamp: Long
)

@JsonClass(generateAdapter = true)
data class KurzListValue(
    val code: String,
    val inCZK: Double
)

fun KurzApiModel.asDomainModel(): KurzModel {
    return KurzModel(
            Base = base,
            CreatedTime = createdTime,
            Rates = rates,
            Success = success,
            Timestamp = timestamp)

}

fun KurzApiModel.asDatabaseModel(): DatabaseKurz {
    return DatabaseKurz(
            id = 0,
            base = base,
            createdTime = createdTime,
            rates = rates,
            success = success,
            timestamp = timestamp)
}
