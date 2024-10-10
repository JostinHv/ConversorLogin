package com.jostin.conversorlogin

import retrofit2.http.GET
import retrofit2.http.Query

interface ExchangeRateApi {
    @GET("v6/469d5e4a041e134248ca7057/latest/PEN")
    suspend fun getLatestRates(@Query("symbols") symbols: String): ExchangeRateResponse
}

data class ExchangeRateResponse(
    val result: String,
    val documentation: String,
    val terms_of_use: String,
    val time_last_update_unix: Long,
    val time_last_update_utc: String,
    val time_next_update_unix: Long,
    val time_next_update_utc: String,
    val base_code: String,
    val conversion_rates: Map<String, Double>
)