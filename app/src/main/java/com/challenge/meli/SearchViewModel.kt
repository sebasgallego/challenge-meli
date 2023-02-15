package com.challenge.meli

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.challenge.meli.product.ProductRepository
import com.challenge.meli.product.model.ProductResponse

class SearchViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    // Expose screen UI product
    private var productRepository: ProductRepository? = null
    private var productLiveData: LiveData<ProductResponse?>? = null

    /**
     * init Repository
     */
    fun initRepository() {
        productRepository = ProductRepository()
        productLiveData = productRepository!!.getMutableLiveData()
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