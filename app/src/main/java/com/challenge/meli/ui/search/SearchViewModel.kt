package com.challenge.meli.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.challenge.meli.ui.product.data.ProductRepository
import com.challenge.meli.ui.product.data.model.ErrorResponse
import com.challenge.meli.ui.product.data.model.ProductResponse

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
    fun getProducts(text: String) {
        if(oldSearch != text){
            oldSearch = text
            productRepository!!.getProducts(text)
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