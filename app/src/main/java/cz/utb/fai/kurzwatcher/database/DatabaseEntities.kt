package cz.utb.fai.kurzwatcher.database

import androidx.lifecycle.LiveData
import androidx.room.Entity
import androidx.room.PrimaryKey
import cz.utb.fai.kurzwatcher.domain.KurzEntryModel
import java.time.LocalDate

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

fun List<DatabaseKurzEntry>.asKurzEntryModel(lastBatch : LiveData<List<KurzEntryModel>>): List<KurzEntryModel> {
    return map {
        for (i in 0 until lastBatch.value!!.size){
            if (it.code == lastBatch.value!![i].Code)
                KurzEntryModel(
                    Code = it.code,
                    CreatedTime = it.createdTime,
                    Rate = it.rate,
                    ChangedBy = it.rate - lastBatch.value!![i].Rate)
        }
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