package com.challenge.meli.ui.product

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.*
import com.bumptech.glide.Glide
import com.challenge.meli.data.ProductRepository
import com.challenge.meli.data.model.*
import com.challenge.meli.utils.NumberHelper
import kotlinx.coroutines.launch
import timber.log.Timber
import java.net.HttpURLConnection

class ProductViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    // Expose screen UI product
    private var productRepository: ProductRepository? = null
    val productLiveData = MutableLiveData<ProductResponse?>()
    val errorCode: MutableLiveData<Int?> get() = _errorCode
    private val _errorCode = MutableLiveData<Int?>()
    val loading = MutableLiveData<Boolean>()

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
    fun getProducts(newValue: String) {
        viewModelScope.launch {
            loading.value = true
            val response = productRepository!!.getProductForName(newValue)
            if (response.httpCode == HttpURLConnection.HTTP_OK) {
                productLiveData.postValue(response.body)
                loading.value = false
                _errorCode.value = null
            } else {
                Timber.e("$_errorCode code: ${response.httpCode}")
                _errorCode.value = response.httpCode
                loading.value = false
            }
        }
    }

    /**
     * Clear old value from LiveData
     */
    fun clear() {
        productLiveData.value = null
        _errorCode.value = null
    }

}