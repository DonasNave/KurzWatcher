package cz.utb.fai.kurzwatcher.api

import com.squareup.moshi.JsonClass
import cz.utb.fai.kurzwatcher.database.DatabaseKurz
import cz.utb.fai.kurzwatcher.domain.KurzModel
import java.text.DecimalFormat
import java.util.Date

@JsonClass(generateAdapter = true)
data class KurzMorphContainer(val kurz: KurzMorph)

/**
 * Videos represent a devbyte that can be played.
 */
@JsonClass(generateAdapter = true)
data class KurzMorph(
    val base: String = "CZK",
    val date: Date,
    val rates: Map<String, Double>,
    val success: Boolean,
    val timestamp: Long
)

/**
 * Convert Network results to database objects
 */
fun KurzMorphContainer.asDomainModel(): List<KurzModel> {
    return kurz.rates.map {
        KurzModel(
            Code = it.key,
            InCZK = 1/it.value)
    }
}

/**
 * Convert Network results to database objects
 */
fun KurzMorphContainer.asDatabaseModel(): List<DatabaseKurz> {
    return kurz.rates.map {
        DatabaseKurz(
            code = it.key,
            inCZK = 1/it.value)
    }
}