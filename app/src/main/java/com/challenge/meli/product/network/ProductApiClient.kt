package com.challenge.meli.product.network

import com.challenge.meli.product.model.ProductResponse
import com.challenge.meli.utils.GlobalsVar.GET_PRODUCTS
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductApiClient {

    @GET(GET_PRODUCTS)
    fun getProducts(
        @Query("q") text: String
    ): Call<ProductResponse?>?

}