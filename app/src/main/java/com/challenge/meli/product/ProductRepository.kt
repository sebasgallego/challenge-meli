package com.challenge.meli.product

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.challenge.meli.core.RetrofitHelper
import com.challenge.meli.product.model.ProductResponse
import com.challenge.meli.product.network.ProductApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class ProductRepository {

    private val productApiClient: ProductApiClient
    private val liveData: MutableLiveData<ProductResponse?> = MutableLiveData<ProductResponse?>()

    /**
     * Repository
     */
    init {
        val retrofit: Retrofit = RetrofitHelper().getRetrofit()
        productApiClient = retrofit.create(ProductApiClient::class.java)
    }

    /**
     * get response
     */
    fun getProducts(text:String) {
        productApiClient.getProducts(text)!!.enqueue(object : Callback<ProductResponse?> {
            override fun onResponse(
                call: Call<ProductResponse?>,
                response: Response<ProductResponse?>
            ) {
               if (response.code() == 200) {
                    liveData.postValue(response.body())
                } else {
                    liveData.postValue(null)
                }
            }

            override fun onFailure(
                call: Call<ProductResponse?>,
                t: Throwable
            ) {
                liveData.postValue(null)
            }
        })
    }

    /**
     * get Response Live Data
     *
     * @return
     */
    fun getMutableLiveData(): LiveData<ProductResponse?> {
        return liveData
    }

}