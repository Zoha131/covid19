package com.conceptgang.coronanews.model


/*
*  "count": 202,
    "next": null,
    "previous": null,*/

data class CoronaClientResult<T>(
    val count: Int,
    val next: String,
    val previous: String,
    val results: List<T>
)