package com.challenge.meli.utils

import com.challenge.meli.product.model.ErrorResponse
import timber.log.Timber

class LogHelper {

    fun saveLogError(errorResponse: ErrorResponse){
        Timber.e("${errorResponse.message} - code: ${errorResponse.code}")
    }

}