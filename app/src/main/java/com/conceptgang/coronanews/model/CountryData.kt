package com.conceptgang.coronanews.model

/*
*
* {
            "id": 4041,
            "country": "USA",
            "cases": "123776",
            "todayCases": "198",
            "deaths": "2229",
            "todayDeaths": "8",
            "recovered": "3231",
            "active": "118316",
            "critical": "2666",
            "casesPerOneMillion": "374",
            "deathsPerOneMillion": "7"
}
*/

data class CountryData(
    val country: String,
    val flag: String,
    val cases: Int,
    val todayCases: Int,
    val deaths: Int,
    val todayDeaths: Int,
    val recovered: Int,
    val active: Int,
    val critical: Int,
    val casesPerOneMillion: Double,
    val deathsPerOneMillion: Double
) {

    companion object{
        val World = "Total"
    }
}


