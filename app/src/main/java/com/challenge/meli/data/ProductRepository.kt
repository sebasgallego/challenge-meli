package com.challenge.meli.data

import com.challenge.meli.core.RetrofitHelper
import com.challenge.meli.data.model.ProductResponse
import com.challenge.meli.data.network.ProductApiClient
import retrofit2.Response
import retrofit2.Retrofit

class ProductRepository {

    private var productApiClient: ProductApiClient

    /**
     * Repository
     */
    init {
        val retrofit: Retrofit = RetrofitHelper().getRetrofit()
        productApiClient = retrofit.create(ProductApiClient::class.java)
    }

    suspend fun getProductForName(value: String): Response<ProductResponse> {
            return productApiClient.getProductForName(value)
    }

}