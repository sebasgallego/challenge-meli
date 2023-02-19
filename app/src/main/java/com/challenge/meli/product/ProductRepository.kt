package com.challenge.meli.product

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.challenge.meli.core.RetrofitHelper
import com.challenge.meli.product.model.ErrorResponse
import com.challenge.meli.product.model.ProductResponse
import com.challenge.meli.product.network.ProductApiClient
import com.challenge.meli.utils.GlobalsVar.FAILURE
import com.challenge.meli.utils.GlobalsVar.SUCCESS
import kotlinx.coroutines.sync.Mutex
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import timber.log.Timber

class ProductRepository {

    private val productApiClient: ProductApiClient
    private val liveData: MutableLiveData<ProductResponse?> = MutableLiveData<ProductResponse?>()
    private val errorMessage = MutableLiveData<ErrorResponse?>()

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
    fun getProducts(text: String) {
        productApiClient.getProducts(text)!!.enqueue(object : Callback<ProductResponse?> {
            override fun onResponse(
                call: Call<ProductResponse?>,
                response: Response<ProductResponse?>
            ) {
                if (response.body() != null && response.code() == SUCCESS) {
                    liveData.postValue(response.body())
                } else {
                    errorMessage.postValue(ErrorResponse(response.message(), response.code()))
                }
            }

            override fun onFailure(
                call: Call<ProductResponse?>,
                t: Throwable
            ) {
                errorMessage.postValue(ErrorResponse(t.message.toString(), FAILURE))
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

    /**
     * get Response Live Data
     *
     * @return
     */
    fun getErrorMutableLiveData(): LiveData<ErrorResponse?> {
        return errorMessage
    }

}