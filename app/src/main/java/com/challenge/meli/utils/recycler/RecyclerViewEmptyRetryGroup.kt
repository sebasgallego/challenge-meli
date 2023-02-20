package com.challenge.meli.utils.recycler

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.challenge.meli.R


class RecyclerViewEmptyRetryGroup(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) :
    RelativeLayout(context, attrs, defStyleAttr) {

    var recyclerView: RecyclerView? = null
        private set
    private var mEmptyView: LinearLayout? = null
    private var textView: TextView? = null
    private var textViewRetry: TextView? = null
    private var mRetryView: LinearLayout? = null
    private var mProgressBar: LinearLayout? = null
    private var mOnRetryClick: OnRetryClick? = null

    constructor(context: Context?) : this(context, null)

    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)

    override fun onViewAdded(child: View) {
        super.onViewAdded(child)
        if (child.id == R.id.recyclerView) {
            recyclerView = findViewById<View>(R.id.recyclerView) as RecyclerView
            return
        }
        if (child.id == R.id.layout_empty) {
            mEmptyView = findViewById<View>(R.id.layout_empty) as LinearLayout
            textView = findViewById<View>(R.id.textView) as TextView
            return
        }
        if (child.id == R.id.layout_retry) {
            textViewRetry = findViewById<View>(R.id.textView_retry) as TextView
            mRetryView = findViewById<View>(R.id.layout_retry) as LinearLayout
            mRetryView!!.setOnClickListener {
                mRetryView!!.visibility = GONE
                mOnRetryClick!!.onRetry()
            }
            return
        }
        if (child.id == R.id.progress_bar) {
            mProgressBar = findViewById<View>(R.id.progress_bar) as LinearLayout
        }
    }

    fun loading() {
        mRetryView!!.visibility = GONE
        mEmptyView!!.visibility = GONE
        mProgressBar!!.visibility = VISIBLE
    }

    fun empty(str:String) {
        mEmptyView!!.visibility = VISIBLE
        mRetryView!!.visibility = GONE
        mProgressBar!!.visibility = GONE
        textView!!.text = str
    }

    fun retry(str:String) {
        mRetryView!!.visibility = VISIBLE
        mProgressBar!!.visibility = GONE
        mEmptyView!!.visibility = GONE
        textViewRetry!!.text = str
    }

    fun success() {
        mRetryView!!.visibility = GONE
        mEmptyView!!.visibility = GONE
        mProgressBar!!.visibility = GONE
    }

    fun setOnRetryClick(onRetryClick: OnRetryClick?) {
        mOnRetryClick = onRetryClick
    }

    interface OnRetryClick {
        fun onRetry()
    }
}