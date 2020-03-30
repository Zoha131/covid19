package com.conceptgang.coronanews.ui.repository

import com.conceptgang.coronanews.data.remote.CoronaClient
import timber.log.Timber

class CoronaRepository (
    private val client: CoronaClient
) {


    suspend fun update(){
        val result = client.getCountryData()


        Timber.tag("CoronaData").d("${result}")

    }


}