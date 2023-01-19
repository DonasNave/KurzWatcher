package cz.utb.fai.kurzwatcher.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import cz.utb.fai.kurzwatcher.domain.KurzModel
import cz.utb.fai.kurzwatcher.domain.TargetValueModel
import java.util.Date


@Entity
data class DatabaseKurz constructor(
    @PrimaryKey
    val code: String,
    val inCZK: Double)

@Entity
data class DatabaseTargetValue constructor(
    @PrimaryKey
    val oldCurCode: String,
    val targetCurCode: String,
    val valueInCZK: Double,
    val createdTime: Date)

fun List<DatabaseKurz>.asKurzModel(): List<KurzModel> {
    return map {
        KurzModel(
            Code = it.code,
            InCZK = it.inCZK)
    }
}

fun List<DatabaseTargetValue>.asTargetValueModel(): List<TargetValueModel> {
    return map {
        TargetValueModel(
            OldCurCode = it.oldCurCode,
            TargetCurCode = it.targetCurCode,
            ValueInCZK = it.valueInCZK,
            CreatedTime = it.createdTime)
    }
}