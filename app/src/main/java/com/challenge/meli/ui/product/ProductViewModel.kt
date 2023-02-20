package com.challenge.meli.ui.product

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.challenge.meli.ui.product.data.ProductRepository
import com.challenge.meli.ui.product.data.model.ErrorResponse
import com.challenge.meli.ui.product.data.model.ProductResponse

class ProductViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    // Expose screen UI product
    private var productRepository: ProductRepository? = null
    private var productLiveData: LiveData<ProductResponse?>? = null
    private var errorLiveData: LiveData<ErrorResponse?>? = null
    // Size of products
    private val _sizeProducts = MutableLiveData<Int>()
    val sizeProducts: LiveData<Int> = _sizeProducts

    /**
     * init Repository
     */
    init {
        productRepository = ProductRepository()
        productLiveData = productRepository!!.getMutableLiveData()
        errorLiveData = productRepository!!.getErrorMutableLiveData()
        defaultValues()
    }

    /**
     * update size products
     */
    fun setSizeProducts(size: Int) {
        _sizeProducts.value = size
    }

    /**
     * Set default values for size products.
     */
    private fun defaultValues() {
        _sizeProducts.value = 0
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
    fun clear(){ productRepository!!.liveData.value = null }

}