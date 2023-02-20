package com.challenge.meli.ui.product

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import com.challenge.meli.data.ProductRepository
import com.challenge.meli.data.model.*
import com.challenge.meli.data.model.AttributeType
import com.challenge.meli.utils.NumberHelper
import timber.log.Timber
import java.text.NumberFormat

class ProductViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    // Expose screen UI product
    private var productRepository: ProductRepository? = null
    private var productLiveData: LiveData<ProductResponse?>? = null
    private var errorLiveData: LiveData<ErrorResponse?>? = null

    //product
    var product = Product()

    // Price of the product
    private val _price = MutableLiveData<Double>()
    val price: LiveData<String> = Transformations.map(_price) {
        // Format the price into the COP currency and return this as LiveData<String>
        NumberHelper().parseAmountToCOP(it)
    }

    companion object {
        @JvmStatic
        @BindingAdapter("glideSrc")
        fun loadImage(
            view: ImageView,
            url: String
        ) { // This methods should not have any return type, = declaration would make it return that object declaration.
            Glide.with(view.context)
                .load(url)
                .circleCrop()
                .into(view)

        }
    }

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
        _price.value = product.price
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
        productRepository!!.liveData.value = null
        productRepository!!.errorMessage.value = null
    }

}