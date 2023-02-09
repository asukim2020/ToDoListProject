package com.asusoft.todolistproject.extension

import android.view.View
import com.asusoft.todolistproject.application.ItemApplication
import com.jakewharton.rxbinding4.view.clicks
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit

fun View.onClick(
    click: () -> Unit
) {
    clicks()
        .throttleFirst(ItemApplication.THROTTLE, TimeUnit.MILLISECONDS)
        .subscribeOn(AndroidSchedulers.mainThread())
        .subscribe {
            click()
        }
}