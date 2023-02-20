package com.challenge.meli.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.challenge.meli.data.ProductRepository
import com.challenge.meli.data.model.ErrorResponse
import com.challenge.meli.data.model.ProductResponse

class SearchViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    // Expose screen UI product
    private var productRepository: ProductRepository? = null
    private var productLiveData: LiveData<ProductResponse?>? = null
    private var errorLiveData: LiveData<ErrorResponse?>? = null
    private var oldSearch = ""

    /**
     * init Repository
     */
    init {
        productRepository = ProductRepository()
        productLiveData = productRepository!!.getMutableLiveData()
        errorLiveData = productRepository!!.getErrorMutableLiveData()
    }

    /**
     * get products
     */
    fun getProducts(newValue: String) {
        if(oldSearch != newValue){
            oldSearch = newValue
            productRepository!!.getProducts(newValue)
        }
    }

    /**
     * get products Response LiveData
     */
    fun getProductsResponseLiveData(): LiveData<ProductResponse?>? {
        return productLiveData
    }

    /**
     * get request error Response LiveData
     */
    fun getErrorResponseLiveData(): LiveData<ErrorResponse?>? {
        return errorLiveData
    }


}