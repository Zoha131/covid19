package com.conceptgang.coronanews.data.remote

import com.conceptgang.coronanews.model.CoronaClientResult
import com.conceptgang.coronanews.model.CountryData
import retrofit2.http.GET

interface CoronaClient {

    @GET("cuurentstat/?search=Total")
    suspend fun getWorldData():CoronaClientResult<CountryData>


    @GET("cuurentstat/")
    suspend fun getCountryData():CoronaClientResult<CountryData>
}