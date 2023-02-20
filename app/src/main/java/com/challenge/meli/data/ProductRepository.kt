package com.challenge.meli.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.challenge.meli.core.RetrofitHelper
import com.challenge.meli.data.model.ErrorResponse
import com.challenge.meli.data.model.ProductResponse
import com.challenge.meli.data.network.NetworkState
import com.challenge.meli.data.network.ProductApiClient
import com.challenge.meli.utils.GlobalsVar.FAILURE
import com.challenge.meli.utils.GlobalsVar.SUCCESS
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class ProductRepository {

    private val productApiClient: ProductApiClient

    /**
     * Repository
     */
    init {
        val retrofit: Retrofit = RetrofitHelper().getRetrofit()
        productApiClient = retrofit.create(ProductApiClient::class.java)
    }

    suspend fun getProductForName(nameProduct: String): NetworkState<ProductResponse> {
        try {
            val response = productApiClient.getProductForName(nameProduct)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) return NetworkState.Success(body)
            }
            return NetworkState.Error(response.message(),response.code())
        } catch (e: Exception) {
            return NetworkState.Error(e.message ?: e.toString(),-1)
        }
    }

}