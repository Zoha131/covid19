package com.conceptgang.coronanews.model

data class HistoryDataResult(
    val country: String,
    val cases: String
)

data class HistoryData(
    val country: String,
    val history: Map<String, Int>
)