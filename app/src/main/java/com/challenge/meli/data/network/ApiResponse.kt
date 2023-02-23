package com.challenge.meli.data.network

import java.net.HttpURLConnection

data class ApiResponse<T>(
    var httpCode: Int = HttpURLConnection.HTTP_OK,
    var body: T? = null,
    var errorMessage: String? = null,
)