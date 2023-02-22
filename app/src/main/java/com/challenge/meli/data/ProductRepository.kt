package com.challenge.meli.data

import android.util.Log
import com.challenge.meli.R
import com.challenge.meli.core.RetrofitHelper
import com.challenge.meli.data.model.ProductResponse
import com.challenge.meli.data.network.ApiResponse
import com.challenge.meli.data.network.ProductApiClient
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Retrofit
import java.io.IOException
import java.net.ConnectException
import java.net.HttpURLConnection
import java.net.UnknownHostException

class ProductRepository {

    private var productApiClient: ProductApiClient

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
        } catch (e: IOException) {
            Firebase.crashlytics.recordException(e)
            return ApiResponse(errorMessage = e.message ?: e.toString(), httpCode = HttpURLConnection.HTTP_UNAVAILABLE)
        } catch (e: Exception) {
            Firebase.crashlytics.recordException(e)
            return ApiResponse(errorMessage = e.message ?: e.toString(), httpCode = HttpURLConnection.HTTP_INTERNAL_ERROR)
        }
    }

}