package cz.utb.fai.kurzwatcher.api

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.QueryMap

fun mapKurzesToParams(kurzes: List<String>): Map<String, String> {
    val params = mutableMapOf<String, String>()
    var currenciesString = ""

    for (kurz in kurzes) {
        currenciesString += kurz + "2%C"
    }

    params["symbols"] = currenciesString.dropLast(3)
    params["base"] = "CZK"
    return params
}

interface KurzService {
    @Headers("apikey: aX9i2BBAByqZGu3YEUC4MW4xM3DwqU1K")
    @GET("latest")
    suspend fun getSpecifiedKurzes(
        @QueryMap options: Map<String, String>
    ): KurzApiModel
}

object KurzesNetBridge {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.apilayer.com/exchangerates_data/")
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    val kurzService: KurzService = retrofit.create(KurzService::class.java)
}
