package cz.utb.fai.kurzwatcher.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import cz.utb.fai.kurzwatcher.domain.KurzModel
import java.util.Date


@Entity
data class DatabaseKurz constructor(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val base: String,
    val createdTime: Date,
    val rates: Map<String, Double>,
    val success: Boolean,
    val timestamp: Long)

fun List<DatabaseKurz>.asKurzModel(): List<KurzModel> {
    return map {
        KurzModel(
            Base = it.base,
            CreatedTime = it.createdTime,
            Rates = it.rates,
            Success = it.success,
            Timestamp = it.timestamp)
    }
}