package com.challenge.meli.ui.product

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.*
import com.bumptech.glide.Glide
import com.challenge.meli.data.ProductRepository
import com.challenge.meli.data.model.*
import com.challenge.meli.data.network.NetworkState
import com.challenge.meli.utils.NumberHelper
import kotlinx.coroutines.launch
import timber.log.Timber

class ProductViewModel() : ViewModel() {
    // TODO: Implement the ViewModel
    // Expose screen UI product
    private var productRepository: ProductRepository? = null
    val productLiveData = MutableLiveData<ProductResponse?>()
    val errorCode: LiveData<Int> get() = _errorCode
    private val _errorCode = MutableLiveData<Int>()
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
            when (val response = productRepository!!.getProductForName(newValue)) {
                is NetworkState.Success -> {
                    productLiveData.postValue(response.data)
                }
                is NetworkState.Error -> {
                    Timber.e("$_errorCode code: ${response.code}")
                    _errorCode.value = response.code
                }
            }
        }
    }

}