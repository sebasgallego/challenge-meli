package com.challenge.meli.utils

import android.app.Activity
import android.widget.Toast
import com.challenge.meli.R
import com.challenge.meli.ui.product.data.model.ErrorResponse
import com.challenge.meli.utils.GlobalsVar.FAILURE

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