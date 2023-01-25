package cz.utb.fai.kurzwatcher.api

import com.google.gson.annotations.SerializedName
import cz.utb.fai.kurzwatcher.database.DatabaseKurzEntry
import cz.utb.fai.kurzwatcher.domain.ConversionResultModel
import cz.utb.fai.kurzwatcher.domain.KurzEntryModel
import java.time.LocalDate

data class ConversionModel(
    val date: String,
    val info: Info,
    val query: Query,
    val result: Double,
    val success: Boolean
)

data class Info(
    val rate: Double,
    val timestamp: Long)

data class Query(
    val amount: Double,
    val from: String,
    val to: String)

data class KurzApiModel(

    @SerializedName("base")
    val base: String,

    @SerializedName("date")
    val createdTime: String,

    @SerializedName("rates")
    val rates: Map<String, Double>,

    @SerializedName("success")
    val success: Boolean,

    @SerializedName("timestamp")
    val timestamp: Long
)

fun ConversionModel.asDomainModel(): ConversionResultModel {
    return ConversionResultModel(
        date = date,
        info = info,
        query = query,
        result = result,
        success = success)
}

fun KurzApiModel.asDomainModel(): List<KurzEntryModel> {
    return rates.map {
        KurzEntryModel(
            Code = it.key,
            CreatedTime = LocalDate.parse(createdTime),
            Rate = it.value)
    }
}

fun KurzApiModel.asDatabaseModel(): List<DatabaseKurzEntry> {
    return rates.map {
        DatabaseKurzEntry(
            id = 0,
            code = it.key,
            createdTime = LocalDate.parse(createdTime),
            rate = 1/it.value,
            timestamp = timestamp)
    }
}
