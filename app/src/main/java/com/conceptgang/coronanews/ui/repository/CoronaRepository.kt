package com.conceptgang.coronanews.ui.repository

import com.conceptgang.coronanews.data.remote.CoronaClient
import com.conceptgang.coronanews.model.CountryData

class CoronaRepository (
    private val client: CoronaClient
) {


    suspend fun getWorldData(): CountryData{
        val result = client.getWorldData()

        return result.results[0]
    }


}