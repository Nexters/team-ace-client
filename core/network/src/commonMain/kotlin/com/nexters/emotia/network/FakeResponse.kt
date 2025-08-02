package com.nexters.emotia.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FakeResponse(
    @SerialName("query")
    val query: String? = null,
    @SerialName("status")
    val status: String,
    @SerialName("country")
    val country: String? = null,
    @SerialName("countryCode")
    val countryCode: String? = null,
    @SerialName("regionName")
    val regionName: String? = null,
    @SerialName("city")
    val city: String? = null,
    @SerialName("zip")
    val zip: String? = null,
    @SerialName("lat")
    val lat: Double? = null,
    @SerialName("lon")
    val lon: Double? = null,
    @SerialName("timezone")
    val timezone: String? = null,
    @SerialName("isp")
    val isp: String? = null,
    @SerialName("org")
    val org: String? = null,
    @SerialName("as")
    val autonomousSystem: String? = null,
    @SerialName("message")
    val message: String? = null
)