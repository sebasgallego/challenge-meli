package com.challenge.meli.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.challenge.meli.data.ProductRepository
import com.challenge.meli.data.model.ProductResponse
import kotlinx.coroutines.launch
import timber.log.Timber
import java.net.HttpURLConnection

class SearchViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    // Expose screen UI product
    private var productRepository: ProductRepository? = null
    val productLiveData = MutableLiveData<ProductResponse?>()
    val errorCode: MutableLiveData<Int?> get() = _errorCode
    private val _errorCode = MutableLiveData<Int?>()
    val loading = MutableLiveData<Boolean>()
    var oldTextSearch = ""
    var isFirstOpen = false

    /**
     * init Repository
     */
    init {
        productRepository = ProductRepository()
    }

    /**
     * get products
     */
    fun getProducts(newValue: String,isRetry:Boolean) {
        if(oldTextSearch != newValue || isRetry){
            oldTextSearch = newValue
            viewModelScope.launch {
                checkFirstOpen()
                val response = productRepository!!.getProductForName(newValue)
                if (response.httpCode == HttpURLConnection.HTTP_OK) {
                    productLiveData.postValue(response.body)
                    loading.value = false
                    _errorCode.value = null
                } else {
                    Timber.e("$_errorCode code: ${response.httpCode}")
                    _errorCode.value = response.httpCode
                    loading.value = false
                }
            }
        }
    }

    /**
     * Check if is First Open
     */
    private fun checkFirstOpen(){
        if(!isFirstOpen){
            loading.value = true
            isFirstOpen = true
        }
    }

    override fun onCleared() {
        super.onCleared()
        Timber.e("onCleared")
    }

}