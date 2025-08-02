package com.nexters.emotia.network

class ApiService(
    private val network: AceNetwork,
) {
    suspend fun getGeolocationInfo(ipAddress: String): FakeResponse {
        return network.get("json/$ipAddress")
    }
}
