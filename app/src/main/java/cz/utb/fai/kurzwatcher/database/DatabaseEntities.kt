package cz.utb.fai.kurzwatcher.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import cz.utb.fai.kurzwatcher.domain.KurzModel
import cz.utb.fai.kurzwatcher.domain.TargetValueModel
import java.text.DateFormat
import java.text.DecimalFormat


@Entity
data class DatabaseKurz constructor(
    @PrimaryKey
    val code: String,
    val name: String,
    val inDollars: DecimalFormat)

@Entity
data class DatabaseTargetValue constructor(
    @PrimaryKey
    val oldCurCode: String,
    val targetCurCode: String,
    val value: DecimalFormat,
    val createdTime: DateFormat)

fun List<DatabaseKurz>.asKurzModel(): List<KurzModel> {
    return map {
        KurzModel(
            Code = it.code,
            Name = it.name,
            InDollars = it.inDollars)
    }
}

fun List<DatabaseTargetValue>.asTargetValueModel(): List<TargetValueModel> {
    return map {
        TargetValueModel(
            OldCurCode = it.oldCurCode,
            TargetCurCode = it.targetCurCode,
            Value = it.value,
            CreatedTime = it.createdTime)
    }
}