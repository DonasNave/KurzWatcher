package cz.utb.fai.kurzwatcher.api

import cz.utb.fai.kurzwatcher.domain.ConversionResultModel
import okhttp3.Interceptor
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.QueryMap

fun mapKurzesToParams(kurzes: List<String>): Map<String, String> {
    val params = mutableMapOf<String, String>()
    var currenciesString = ""

    for (kurz in kurzes) {
        currenciesString += "$kurz,"
    }

    params["base"] = "CZK"
    params["symbols"] = currenciesString.dropLast(3)
    return params
}

interface KurzService {
    @Headers("apikey: aX9i2BBAByqZGu3YEUC4MW4xM3DwqU1K")
    @GET("latest")
    suspend fun getSpecifiedKurzes(
        @QueryMap options: Map<String, String>
    ): Response<KurzApiModel>

    @Headers("apikey: aX9i2BBAByqZGu3YEUC4MW4xM3DwqU1K")
    @GET("convert")
    suspend fun convertCurrency(
        @retrofit2.http.Query("from") from : String,
        @retrofit2.http.Query("to") to : String,
        @retrofit2.http.Query("amount") amount : Double
    ): Response<ConversionResultModel>
}

object UserService{

    fun getClient() : Retrofit = Retrofit.Builder()
        .baseUrl("https://api.apilayer.com/exchangerates_data/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

class KurzesNetBridge {

    private val retrofit = UserService.getClient()
    val userApi = retrofit.create(KurzService::class.java)

}
