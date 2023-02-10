package com.asusoft.todolistproject.extension

import android.app.Application
import android.view.View
import com.asusoft.todolistproject.application.ItemApplication
import com.jakewharton.rxbinding4.view.clicks
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import java.util.concurrent.TimeUnit

fun View.onClick(
    click: () -> Unit
) {
    clicks()
        .throttleFirst(ItemApplication.THROTTLE, TimeUnit.MILLISECONDS)
        .delay(ItemApplication.CLICK_DELAY, TimeUnit.MILLISECONDS)
        .subscribeOn(AndroidSchedulers.mainThread())
        .subscribe {
            click()
        }
}