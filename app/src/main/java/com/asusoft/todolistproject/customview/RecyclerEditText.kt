package com.asusoft.todolistproject.customview

import android.content.Context
import androidx.appcompat.widget.AppCompatEditText
import android.text.TextWatcher
import android.util.AttributeSet
import java.util.ArrayList

class RecyclerEditText : AppCompatEditText {
    private var mListeners: ArrayList<TextWatcher>? = null

    constructor(ctx: Context?) : super(ctx!!) {}
    constructor(ctx: Context?, attrs: AttributeSet?) : super(ctx!!, attrs) {}
    constructor(ctx: Context?, attrs: AttributeSet?, defStyle: Int) : super(
        ctx!!, attrs, defStyle
    ) {
    }

    override fun addTextChangedListener(watcher: TextWatcher) {
        if (mListeners == null) {
            mListeners = ArrayList()
        }
        mListeners!!.add(watcher)
        super.addTextChangedListener(watcher)
    }

    override fun removeTextChangedListener(watcher: TextWatcher) {
        if (mListeners != null) {
            val i = mListeners!!.indexOf(watcher)
            if (i >= 0) {
                mListeners!!.removeAt(i)
            }
        }
        super.removeTextChangedListener(watcher)
    }

    fun clearTextChangedListeners() {
        if (mListeners != null) {
            for (watcher in mListeners!!) {
                super.removeTextChangedListener(watcher)
            }
            mListeners!!.clear()
            mListeners = null
        }
    }
}