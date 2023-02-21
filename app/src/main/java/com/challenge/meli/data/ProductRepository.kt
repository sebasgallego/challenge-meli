package com.challenge.meli.data

import com.challenge.meli.core.RetrofitHelper
import com.challenge.meli.data.model.ProductResponse
import com.challenge.meli.data.network.ApiResponse
import com.challenge.meli.data.network.ProductApiClient
import retrofit2.Retrofit

class ProductRepository {

      var productApiClient: ProductApiClient

    /**
     * Repository
     */
    init {
        val retrofit: Retrofit = RetrofitHelper().getRetrofit()
        productApiClient = retrofit.create(ProductApiClient::class.java)
    }

    suspend fun getProductForName(nameProduct: String): ApiResponse<ProductResponse> {
        try {
            val response = productApiClient.getProductForName(nameProduct)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) return ApiResponse(body = body)
            }
            return ApiResponse(errorMessage = response.message(), httpCode = response.code())
        } catch (e: Exception) {
            return ApiResponse(errorMessage = e.message ?: e.toString(), httpCode = -1)
        }
    }

}