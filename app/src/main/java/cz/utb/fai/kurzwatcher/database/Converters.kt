package cz.utb.fai.kurzwatcher.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.time.LocalDate
import java.util.*

class DateConverter {
    @TypeConverter
    fun fromTimestamp(value: String?): LocalDate? {
        return value?.let { LocalDate.parse(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: LocalDate?): String? {
        return date.toString()
    }
}
class StringMapConverter {
    @TypeConverter
    fun fromString(value: String?): Map<String?, Double?>? {
        val mapType: Type = object : TypeToken<Map<String?, Double?>?>() {}.type
        return Gson().fromJson(value, mapType)
    }

    @TypeConverter
    fun fromStringMap(map: Map<String?, Double?>?): String? {
        val gson = Gson()
        return gson.toJson(map)
    }
}