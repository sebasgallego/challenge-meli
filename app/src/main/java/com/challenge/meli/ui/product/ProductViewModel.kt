package com.challenge.meli.ui.product

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.*
import com.bumptech.glide.Glide
import com.challenge.meli.domain.GetProductsUseCase
import com.challenge.meli.data.ProductRepository
import com.challenge.meli.data.model.*
import com.challenge.meli.utils.NumberHelper
import kotlinx.coroutines.launch
import timber.log.Timber
import java.net.HttpURLConnection

class ProductViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    // Expose screen UI product
    var productRepository: ProductRepository? = null
    var getProductsUseCase: GetProductsUseCase? = null
    val productLiveData = MutableLiveData<ProductResponse>()
    val errorCode: MutableLiveData<Int?> get() = _errorCode
    private val _errorCode = MutableLiveData<Int?>()
    val loading = MutableLiveData<Boolean>()
    var isSuccess: Boolean = false

    //product
    val product: MutableLiveData<Product> = MutableLiveData()

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
        getProductsUseCase = GetProductsUseCase(productRepository!!)
        default()
    }

    /**
     * Default data
     */
    private fun default() {
        isSuccess = false
    }

    /**
     * set product selected
     */
    fun setProductSelected(_product: Product) {
        product.value = _product
        _price.value = _product.price
    }

    /**
     * get products
     */
    fun getProducts(newValue: String) {
        viewModelScope.launch {
            loading.value = true
            val response = getProductsUseCase!!(newValue)
            if (response!!.httpCode == HttpURLConnection.HTTP_OK) {
                productLiveData.postValue(response.body!!)
                loading.value = false
                _errorCode.value = null
                isSuccess = true
            } else {
                Timber.e("$${response.errorMessage} code: ${response.httpCode}")
                _errorCode.value = response.httpCode
                loading.value = false
            }
        }
    }

}