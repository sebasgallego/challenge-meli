package com.challenge.meli.domain


import com.challenge.meli.data.ProductRepository
import com.challenge.meli.data.model.ProductResponse
import com.challenge.meli.data.network.ApiResponse
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import java.io.IOException
import java.net.HttpURLConnection

class GetProductsUseCase constructor(private val repository: ProductRepository) {
    suspend operator fun invoke(value: String): ApiResponse<ProductResponse>? {
        try {
            val response = repository.getProductForName(value)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null)
                    return ApiResponse(body = body)
            }
            return ApiResponse(
                errorMessage = response.message(),
                httpCode = response.code(),
                body = ProductResponse(ArrayList())
            )
        } catch (e: IOException) {
            return ApiResponse(
                errorMessage = e.message ?: e.toString(),
                httpCode = HttpURLConnection.HTTP_UNAVAILABLE
            )
        } catch (e: Exception) {
            Firebase.crashlytics.recordException(e)
            return ApiResponse(
                errorMessage = e.message ?: e.toString(),
                httpCode = HttpURLConnection.HTTP_INTERNAL_ERROR
            )
        }
    }
}