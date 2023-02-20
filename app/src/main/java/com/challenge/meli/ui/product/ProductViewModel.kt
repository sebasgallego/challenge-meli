package com.challenge.meli.ui.product

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.challenge.meli.data.ProductRepository
import com.challenge.meli.data.model.ErrorResponse
import com.challenge.meli.data.model.ProductResponse

class ProductViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    // Expose screen UI product
    private var productRepository: ProductRepository? = null
    private var productLiveData: LiveData<ProductResponse?>? = null
    private var errorLiveData: LiveData<ErrorResponse?>? = null


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
        productRepository!!.getProducts(text)
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

    /**
     * Clear old value from LiveData
     */
    fun clear() {
       /* productRepository!!.liveData.value = null
        productRepository!!.errorMessage.value = null*/
    }

}