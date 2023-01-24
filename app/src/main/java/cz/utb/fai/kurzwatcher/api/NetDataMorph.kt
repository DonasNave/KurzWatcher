package cz.utb.fai.kurzwatcher.api

import com.squareup.moshi.JsonClass
import cz.utb.fai.kurzwatcher.database.DatabaseKurzEntry
import cz.utb.fai.kurzwatcher.domain.KurzEntryModel
import java.time.LocalDate
import java.util.Date

@JsonClass(generateAdapter = true)
data class KurzApiModel(
    val base: String,
    val createdTime: LocalDate,
    val rates: Map<String, Double>,
    val success: Boolean,
    val timestamp: Long
)

fun KurzApiModel.asDomainModel(): List<KurzEntryModel> {
    return rates.map {
        KurzEntryModel(
            Code = it.key,
            CreatedTime = createdTime,
            Rate = it.value)
    }

}

fun KurzApiModel.asDatabaseModel(): List<DatabaseKurzEntry> {
    return rates.map {
        DatabaseKurzEntry(
            id = 0,
            code = it.key,
            createdTime = createdTime,
            rate = it.value,
            timestamp = timestamp)
    }
}
