package com.challenge.meli.ui.product

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.challenge.meli.data.ProductRepository
import com.challenge.meli.data.model.*
import com.challenge.meli.data.model.AttributeType
import timber.log.Timber

class ProductViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    // Expose screen UI product
    private var productRepository: ProductRepository? = null
    private var productLiveData: LiveData<ProductResponse?>? = null
    private var errorLiveData: LiveData<ErrorResponse?>? = null
    var product = Product()

    /**
     * init Repository
     */
    init {
        productRepository = ProductRepository()
        productLiveData = productRepository!!.getMutableLiveData()
        errorLiveData = productRepository!!.getErrorMutableLiveData()
    }

    /**
     * set product selected
     */
    fun setProductSelected(_product: Product) {
        product = _product
        Timber.e("DEBUG"+ product.getItemAttributes(AttributeType.ITEM_CONDITION.toString()))
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