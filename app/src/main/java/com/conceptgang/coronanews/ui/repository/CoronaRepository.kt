package com.conceptgang.coronanews.ui.repository

import com.conceptgang.coronanews.data.remote.CoronaClient
import com.conceptgang.coronanews.model.CountryData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class CoronaRepository(
    private val client: CoronaClient
) {


    suspend fun getWorldData(): CountryData {
        val result = client.getWorldData()

        return result.results[0]
    }


    suspend fun getCountryData(): List<CountryData> {
        val result = client.getCountryData()

        return result.results
    }


    suspend fun getHistoryData(): Map<String, Map<String, Int>> = withContext(Dispatchers.Default) {

        val countryMap: MutableMap<String, Map<String, Int>> = HashMap()

        try {
            val result = client.getHistoryData().results

            result.forEach { data ->

                val country = if(data.country.contains("hong_kong")) "hong kong" else data.country.split("_")[0].removeSuffix("*")

                val bojha =
                    data
                        .cases
                        .removePrefix("{")
                        .removeSuffix("}")
                        .split(",")
                        .map { entry ->
                            val vangachura = entry.split(":")
                            vangachura[0].trim() to vangachura[1].trim().toInt()
                        }.toMap()



                if (countryMap.containsKey(country)) {

                    val oldMap = countryMap.getValue(country)
                    val newMap = oldMap.map { entry ->
                        val oldValue = entry.value
                        val newValue = oldValue + bojha.getValue(entry.key)
                        entry.key to newValue
                    }.toMap()

                    countryMap[country] = newMap
                } else {
                    countryMap[country] = bojha
                }


                if (country.contains("china", false)) Timber.tag("HISTORYDATA").d("country: ${country} ")

            }

            Timber.tag("HISTORYDATA").d(" \n\n\n\n data: ${countryMap["china"]}")


        } catch (ex: Exception) {
            Timber.tag("HISTORYDATA").d("error: ${ex.message} ")
        }

        countryMap
    }


}