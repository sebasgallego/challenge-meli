package com.challenge.meli.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.challenge.meli.data.ProductRepository
import com.challenge.meli.data.model.ProductResponse
import com.challenge.meli.data.network.NetworkState
import kotlinx.coroutines.launch
import timber.log.Timber

class SearchViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    // Expose screen UI product
    private var productRepository: ProductRepository? = null
    val productLiveData = MutableLiveData<ProductResponse?>()
    val errorCode: LiveData<Int> get() = _errorCode
    private val _errorCode = MutableLiveData<Int>()
    val loading = MutableLiveData<Boolean>()


    /**
     * init Repository
     */
    init {
        productRepository = ProductRepository()
    }

    /**
     * get products
     */
    fun getProducts(newValue: String) {
        viewModelScope.launch {
            loading.value = true
            when (val response = productRepository!!.getProductForName(newValue)) {
                is NetworkState.Success -> {
                    productLiveData.postValue(response.data)
                }
                is NetworkState.Error -> {
                    Timber.e("$_errorCode code: ${response.code}")
                    _errorCode.value = response.code
                }
            }
        }
    }

}