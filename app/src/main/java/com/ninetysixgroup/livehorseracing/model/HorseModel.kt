package com.ninetysixgroup.livehorseracing.api

data class HorseModel(
    val id: String,
    val channel: String,
    val channelName: String,
    val racingDate: String,
    val racingTime: String,
    val image: Int,
    val viewType: Int,
)

data class videoModel(val status: String?, val H5LINKROW: String?)
