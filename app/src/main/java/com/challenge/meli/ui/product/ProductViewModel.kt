package com.challenge.meli.ui.product

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.challenge.meli.ui.product.data.ProductRepository
import com.challenge.meli.ui.product.data.model.ErrorResponse
import com.challenge.meli.ui.product.data.model.ProductResponse

class ProductViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    // Expose screen UI product
    private var productRepository: ProductRepository? = null
    private var productLiveData: LiveData<ProductResponse?>? = null
    private var errorLiveData: LiveData<ErrorResponse?>? = null
    // Quantity of cupcakes in this order
    private val _sizeProducts = MutableLiveData<Int>()
    val sizeProducts: LiveData<Int> = _sizeProducts
    private val _nameProduct = MutableLiveData<String>()

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
     * set name selected
     */
      fun setNameSelect(nameProduct: String) {
        _nameProduct.value = nameProduct
    }

    /**
     * get name selected
     */
    fun getNameSelect(): String? {
        return _nameProduct.value
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

}