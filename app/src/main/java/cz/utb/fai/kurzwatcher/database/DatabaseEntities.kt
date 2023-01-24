package cz.utb.fai.kurzwatcher.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import cz.utb.fai.kurzwatcher.domain.KurzEntryModel
import java.time.LocalDate
import java.util.Date


@Entity
data class DatabaseKurzEntry constructor(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val code: String,
    val createdTime: LocalDate,
    val rate: Double,
    val timestamp: Long
)

fun List<DatabaseKurzEntry>.asKurzEntryModel(): List<KurzEntryModel> {
    return map {
        KurzEntryModel(
            Code = it.code,
            CreatedTime = it.createdTime,
            Rate = it.rate)

    }
}

fun DatabaseKurzEntry.asKurzEntryModel(): KurzEntryModel {
    return KurzEntryModel(
        Code = this.code,
        CreatedTime = this.createdTime,
        Rate = this.rate)
}