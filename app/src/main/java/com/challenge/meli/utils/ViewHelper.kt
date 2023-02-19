package com.challenge.meli.utils

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.navigation.NavController
import com.challenge.meli.R
import com.challenge.meli.product.model.ErrorResponse
import com.challenge.meli.utils.GlobalsVar.FAILURE
import com.challenge.meli.utils.GlobalsVar.SUCCESS
import com.google.android.material.appbar.MaterialToolbar
import timber.log.Timber

class ViewHelper(activity: Activity) {

    private var mActivity: Activity? = activity

    init {
        mActivity = activity
    }

    private fun precessMsgError(errorResponse: ErrorResponse): String {
        return when (errorResponse.code) {
            FAILURE -> mActivity!!.getString(R.string.error_internet)
            else -> {
                mActivity!!.getString(R.string.error_server)
            }
        }
    }

    fun showMsgError(errorResponse: ErrorResponse){
        Toast.makeText(mActivity, precessMsgError(errorResponse), Toast.LENGTH_LONG).show()
    }

}