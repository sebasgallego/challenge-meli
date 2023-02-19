package com.challenge.meli

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.challenge.meli.product.ProductRepository
import com.challenge.meli.product.model.ProductResponse

class ProductViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    // Expose screen UI product
    private var productRepository: ProductRepository? = null
    private var productLiveData: LiveData<ProductResponse?>? = null
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
        defaultValues()
        getProducts(_nameProduct.value.toString())
    }

    /**
     * Set default values for size products.
     */
      fun setNameSelect(nameProduct: String) {
        _nameProduct.value = nameProduct
    }

    /**
     * Set default values for size products.
     */
    private fun defaultValues() {
        _sizeProducts.value = 0
    }

    /**
     * update size products
     */
    fun setSizeProducts(size: Int) {
        _sizeProducts.value = size
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

}