package com.challenge.meli.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.challenge.meli.data.ProductRepository
import com.challenge.meli.data.model.ProductResponse
import com.challenge.meli.domain.GetProductsUseCase
import kotlinx.coroutines.launch
import timber.log.Timber
import java.net.HttpURLConnection

class SearchViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    // Expose screen UI product
    private var productRepository: ProductRepository? = null
    var getProductsUseCase: GetProductsUseCase? = null
    val productLiveData = MutableLiveData<ProductResponse>()
    val errorCode: MutableLiveData<Int?> get() = _errorCode
    private val _errorCode = MutableLiveData<Int?>()
    val loading = MutableLiveData<Boolean>()
    lateinit var oldTextSearch: String
    private var isFirstOpen: Boolean = false

    /**
     * init Repository
     */
    init {
        productRepository = ProductRepository()
        getProductsUseCase = GetProductsUseCase(productRepository!!)
        default()
    }

    /**
     * Default data
     */
    private fun default() {
        oldTextSearch = ""
        isFirstOpen = false
    }

    /**
     * get products
     */
    fun getProducts(newValue: String) {
        viewModelScope.launch {
            checkFirstOpen()
            val response = getProductsUseCase!!(newValue)
            if (response!!.httpCode == HttpURLConnection.HTTP_OK) {
                productLiveData.postValue(response.body!!)
                loading.value = false
                _errorCode.value = null
                oldTextSearch = newValue
            } else {
                Timber.e("$${response.errorMessage} code: ${response.httpCode}")
                _errorCode.value = response.httpCode
                loading.value = false
            }
        }
    }

    /**
     * Check if is First Open for show loading
     */
    private fun checkFirstOpen() {
        if (!isFirstOpen) {
            loading.value = true
            isFirstOpen = true
        }
    }

}